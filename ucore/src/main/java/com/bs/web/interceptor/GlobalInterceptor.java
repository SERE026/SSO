package com.bs.web.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.bs.api.modle.Constants;
import com.bs.api.modle.User;
import com.bs.api.service.UserService;
import com.bs.service.util.SpringContextHolder;


public class GlobalInterceptor  implements HandlerInterceptor {

	private static Logger LOG = LoggerFactory.getLogger(GlobalInterceptor.class);
	
	private UserService userService = SpringContextHolder.getBean(UserService.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		response.setHeader("progma","no-cache");  
	    response.setHeader("Cache-Control","no-cache");  
	    response.setDateHeader("Expires",0);
		
		String uri = request.getRequestURI();
		String uriPrefix = request.getContextPath();
		
		//使用JSESSIONID 来保存会话，模拟tomcat 的session实现
		String JSESSIONID = request.getParameter(Constants.CACHE_COOKIE_KEY);  //此处需要验签
		
		if(StringUtils.isBlank(JSESSIONID)) JSESSIONID = getJSessionId(request);
		
		//客户端存储cookie
		addCookie(request,response,JSESSIONID);
		
		User user = userService.queryUserByKey(JSESSIONID);
		
		if(uri.equals("/") || StringUtils.startsWith(uri,uriPrefix+"/js/") 
				|| StringUtils.startsWith(uri,uriPrefix+"/images/")
				|| StringUtils.startsWith(uri,uriPrefix+"/css/")
				|| StringUtils.startsWith(uri,uriPrefix+"/servlet/")
				|| StringUtils.startsWith(uri,uriPrefix+"/unck/")
				|| StringUtils.startsWith(uri,uriPrefix+"/login")
				|| StringUtils.startsWith(uri,uriPrefix+"/p/")
				|| StringUtils.startsWith(uri,uriPrefix+"/register"))
		{
			LOG.info("拦截器直接放过的地址：{}",uri);
		}
		else
		{
			
			if(user ==null)
			{
				response.sendRedirect(uriPrefix+"/login");
				return false;
			}
		}
		if(user != null) userService.expire(JSESSIONID, Constants.EXPIRETIME);
		return true;
	}

	/**
	 * 取cookie中的SessionId
	 * @param request
	 * @param JSESSIONID
	 */
	private String getJSessionId(HttpServletRequest request ) {
		String JSESSIONID = null;
		Cookie[] cookies = request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			if (Constants.CACHE_COOKIE_KEY.equals(cookies[i].getName())) {
				JSESSIONID = cookies[i].getValue();
				break;
			}
		}
		if(StringUtils.isBlank(JSESSIONID)) JSESSIONID = request.getSession().getId();
		return JSESSIONID;
	}

	/**
	 * 客户端存储cookie
	 * @param request
	 * @param response
	 * @param JSESSIONID
	 */
	private void addCookie(HttpServletRequest request,HttpServletResponse response, String JSESSIONID) {
		String path = request.getContextPath();
		try {
			Cookie cookie = new Cookie(Constants.CACHE_COOKIE_KEY, JSESSIONID); // 保存昵称到cookie
			cookie.setPath(path + "/");
			cookie.setMaxAge(Constants.COOKIE_ALIVE);
			cookie.setSecure(false);
			response.addCookie(cookie);
		} catch (Exception e) {
			LOG.error("存放cookie失败:"+e.getMessage());
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		
	}
}
