package com.bs.web.util;


import javax.servlet.http.HttpServletRequest;

import com.bs.api.modle.UConstants;
import com.bs.api.modle.User;
import com.bs.api.service.SessionManagerService;

public class SessionUtil {
	
	public static User getUserFromSession(HttpServletRequest request) {

		return (User) request.getSession().getAttribute(UConstants.CACHE_COOKIE_KEY);
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
		User user = (User)request.getSession().getAttribute(UConstants.CACHE_COOKIE_KEY);
		String name = request.getParameter(UConstants.USER_NAME);
		if(user==null){
			user = sessionManagerService.queryUserBySID(UConstants.CACHE_COOKIE_KEY,name);
		}
		return user;
	}
}
