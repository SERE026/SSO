package com.bs.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.bs.api.model.User;
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
		
		
		/**
		 *  用户中心查询
		 */
		User user = SessionUtil.getUserFromSession(request);
		
		if(uri.equals("/") || StringUtils.startsWith(uri,uriPrefix+"/js/") 
				|| StringUtils.startsWith(uri,uriPrefix+"/images/")
				|| StringUtils.startsWith(uri,uriPrefix+"/css/")
				|| StringUtils.startsWith(uri,uriPrefix+"/servlet/")
				|| StringUtils.startsWith(uri,uriPrefix+"/unck/")
				|| StringUtils.startsWith(uri,uriPrefix+"/login")
				|| StringUtils.startsWith(uri,uriPrefix+"/index")
				|| StringUtils.startsWith(uri,uriPrefix+"/p")
				|| StringUtils.startsWith(uri,uriPrefix+"/setKey")
				|| StringUtils.startsWith(uri,uriPrefix+"/expire")
				|| StringUtils.startsWith(uri,uriPrefix+"/register"))
		{
			LOG.info("不经过拦截器处理的url地址：{}",uri);
		}
		else
		{
			
			if(user ==null)
			{
				response.sendRedirect(uriPrefix+"/login");
				return false;
			}	
		}
		
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
	}
}
