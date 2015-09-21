package com.bs.web.interceptor;

import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;

import com.bs.api.modle.Constants;
import com.bs.api.modle.User;
import com.bs.api.service.UserService;
import com.bs.service.util.Global;
import com.bs.service.util.SpringContextHolder;
import com.bs.web.util.TokenUtils;

/**
 * 子域名单点登录
 * @author think
 *
 */
public class ChildGlobalInterceptor  implements HandlerInterceptor {

	private static Logger LOG = LoggerFactory.getLogger(ChildGlobalInterceptor.class);
	private UserService userService = SpringContextHolder.getBean(UserService.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		response.setHeader("progma","no-cache");  
	    response.setHeader("Cache-Control","no-cache");  
	    response.setDateHeader("Expires",0);
		
		String uri = request.getRequestURI();
		String uriPrefix = request.getContextPath();
		
		
		String tkey = "";
		Cookie[] cookies = request.getCookies(); // 读取cookie
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (Constants.JVMCACHE_KEY.equals(cookie.getName())) {
					tkey = cookie.getValue();
					cookie.setMaxAge(Constants.EXPIRETIME.intValue());
//					cookie.setHttpOnly(true);
					cookie.setPath("/");
					cookie.setDomain(".wjw.com");
					response.addCookie(cookie);
				}
			}
		}
		
		if(uri.equals("/") || StringUtils.startsWith(uri,uriPrefix+"/js/") 
				|| StringUtils.startsWith(uri,uriPrefix+"/images/")
				|| StringUtils.startsWith(uri,uriPrefix+"/css/")
				|| StringUtils.startsWith(uri,uriPrefix+"/servlet/")
				|| StringUtils.startsWith(uri,uriPrefix+"/unck/")
				|| StringUtils.startsWith(uri,uriPrefix+"/login")
				|| StringUtils.startsWith(uri,uriPrefix+"/p")
				|| StringUtils.startsWith(uri,uriPrefix+"/register"))
		{
			
		}
		else
		{
			
			
			User user = null ;
			if(tkey != null && !"".equals(tkey)) user = userService.queryUserByKey(tkey);
			
			if(user ==null)
			{

				
				LOG.info("请求参数：>>> "+request.getQueryString());
		        Map<String, String[]> params = request.getParameterMap();  
		        String queryString = "?";  
		        for (String key : params.keySet()) {  
		            String[] values = params.get(key);  
		            for (int i = 0; i < values.length; i++) {  
		                String value = values[i];  
		                queryString += key + "=" + value + "&";  
		            }  
		        }  
		        // 去掉最后一个空格  
		        queryString = queryString.substring(0, queryString.length() - 1);  
				
				StringBuffer all=request.getRequestURL();
				int index=all.indexOf(uri);
				String backurl=request.getRequestURI();
				//回调地址
				backurl = all.substring(0,index)+backurl+queryString;
				backurl=URLEncoder.encode(backurl, "utf-8");
				
				response.sendRedirect(Global.getConfig(Constants.SSOURL)+"?service="+backurl);
				
				return false;
			}	
		}
		
		//TODO 更新用户在缓存中的值
		if(StringUtils.isNotBlank(tkey)) userService.expire(tkey, Constants.EXPIRETIME);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		
	}
}
