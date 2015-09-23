package com.bs.web.util;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.bs.api.modle.UConstants;
import com.bs.api.modle.User;
import com.bs.api.service.SessionManagerService;

public class SessionUtil {
	
	public static User getUserFromSession(HttpServletRequest request) {

		return (User) request.getSession().getAttribute(getJSessionId(request));
	}
	
	public static boolean invalidateSession(HttpServletRequest request) {
		
		request.getSession().removeAttribute(getJSessionId(request));
		request.getSession().invalidate();
		return true;
	}
	
	public static Object getAttribute(HttpServletRequest request,String name){
		return request.getSession().getAttribute(name);
	}
	
	public static void setAttribute(HttpServletRequest request,String name,Object value){
		request.getSession().setAttribute(name,value);
	}
	
	/**
	 * 取cookie中的SessionId
	 * @param request
	 * @param JSESSIONID
	 */
	public static String getJSessionId(HttpServletRequest request ) {
		String JSESSIONID = request.getParameter(UConstants.CACHE_COOKIE_KEY);  //此处需要验签
		if(!StringUtils.isBlank(JSESSIONID))   return JSESSIONID;
		
		Cookie[] cookies = request.getCookies();
		if(cookies !=null ){
			for (int i = 0; i < cookies.length; i++) {
				if (UConstants.CACHE_COOKIE_KEY.equals(cookies[i].getName())) {
					JSESSIONID = cookies[i].getValue();
					break;
				}
			}
		}
		if(StringUtils.isBlank(JSESSIONID)) JSESSIONID = request.getSession().getId();
		return JSESSIONID;
	}
	
	public static User getUserFromSession(HttpServletRequest request,
			SessionManagerService sessionManagerService,String JSESSIONID) {
		User user = (User)request.getSession().getAttribute(JSESSIONID);
		String http_flag = request.getParameter(UConstants.HTTP_FLAG);
		if(user==null){
			user = sessionManagerService.queryUserBySID(JSESSIONID);
		}
		return user;
	}
}
