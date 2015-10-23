package com.bs.web.util;

import javax.servlet.http.HttpServletRequest;

import com.bs.api.model.UConstants;
import com.bs.api.model.User;
import com.bs.api.service.SessionManagerService;

public class SessionUtil {
	
	public static User getUserFromSession(HttpServletRequest request) {
		
		return  (User)request.getSession().getAttribute(UConstants.CACHE_COOKIE_KEY);
	}
	
	public static boolean invalidateSession(HttpServletRequest request) {
		
		request.getSession().removeAttribute(UConstants.CACHE_COOKIE_KEY);
		request.getSession().invalidate();
		return true;
	}
	
	public static Object getAttribute(HttpServletRequest request,String name){
		return request.getSession().getAttribute(name);
	}
	
	public static void setAttribute(HttpServletRequest request,String name,Object value){
		request.getSession().setAttribute(name,value);
	}

	public static User getUserFromSession(HttpServletRequest request,
			SessionManagerService sessionManagerService) {
		String name = request.getParameter(UConstants.USER_NAME);
		User user = (User)request.getSession().getAttribute(UConstants.CACHE_COOKIE_KEY);
		if(user==null ){
			//TODO 由于拦截器和过滤器是线程安全的，不支持httpClient调用
			user = sessionManagerService.queryUserBySID(UConstants.CACHE_COOKIE_KEY,name);
		}
		return user;
	}
	
}
