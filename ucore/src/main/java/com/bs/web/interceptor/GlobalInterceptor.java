package com.bs.web.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.bs.api.modle.UConstants;
import com.bs.api.modle.User;
import com.bs.api.service.SessionManagerService;
import com.bs.service.util.SpringContextHolder;
import com.bs.web.util.SessionUtil;


public class GlobalInterceptor  implements HandlerInterceptor {

	private static Logger LOG = LoggerFactory.getLogger(GlobalInterceptor.class);
	
	private SessionManagerService sessionManagerService = SpringContextHolder.getBean(SessionManagerService.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		response.setHeader("progma","no-cache");  
	    response.setHeader("Cache-Control","no-cache");  
	    response.setDateHeader("Expires",0);
		
		String uri = request.getRequestURI();
		String uriPrefix = request.getContextPath();
		
		//使用JSESSIONID 来保存会话，模拟tomcat 的session实现
		String JSESSIONID =  SessionUtil.getJSessionId(request);
		
		//客户端存储cookie
		addCookie(request,response,JSESSIONID);
		
//		User user = sessionManagerService.queryUserBySID(JSESSIONID);
		User user = SessionUtil.getUserFromSession(request);
		
		if(uri.equals("/") || StringUtils.startsWith(uri,uriPrefix+"/js/") 
				|| StringUtils.startsWith(uri,uriPrefix+"/images/")
				|| StringUtils.startsWith(uri,uriPrefix+"/css/")
				|| StringUtils.startsWith(uri,uriPrefix+"/servlet/")
				|| StringUtils.startsWith(uri,uriPrefix+"/unck/")
				|| StringUtils.startsWith(uri,uriPrefix+"/login")
				|| StringUtils.startsWith(uri,uriPrefix+"/index")
				|| StringUtils.startsWith(uri,uriPrefix+"/p/")
				|| StringUtils.startsWith(uri,uriPrefix+"/setKey")
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
		if(user != null) sessionManagerService.expire(JSESSIONID, UConstants.EXPIRETIME);
		return true;
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
			Cookie cookie = new Cookie(UConstants.CACHE_COOKIE_KEY, JSESSIONID); // 保存昵称到cookie
			cookie.setPath(path + "/");
			cookie.setMaxAge(UConstants.COOKIE_ALIVE);
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
