package com.bs.web.controller;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bs.api.modle.Constants;
import com.bs.api.modle.User;
import com.bs.api.service.UserService;
import com.bs.web.cache.JVMCache;

@Controller
public class LoginController {
	
	
	public static final String USERNAME = "cloud" ;
	public static final String PASSWORD = "cloud";
	
	@Autowired
	private UserService userService;

	@RequestMapping("/login")
	public String toLogin(HttpServletRequest request, HttpServletResponse response,Model model){
		
		model.addAttribute("service",request.getParameter("service"));
		return "login";
	}
	
	
	
	/**
	 * 完全跨域名单点登录
	 * 
	 * */
	@ResponseBody
	@RequestMapping("/login/submit")
	public String login(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String uriPrefix = request.getContextPath();

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		// String service = request.getParameter("service");

		String service = request.getHeader("Referer");

		String JSESSIONID = request.getSession().getId();
		/***
		 * 1. 此处用户名密码从缓存中获取， 2. 如果缓存中没有从关系型数据库获取
		 **/
		User user = null;
		if (JSESSIONID != null && !"".equals(JSESSIONID))
			user = userService.queryUserByKey(JSESSIONID); // 1.此处用户名密码从缓存中获取，

		if (user == null) {
			user = userService.queryUserByOther(username); // 2.缓存中没有从关系型数据库获取
		}

		if (user != null && user.getName().equals(username)
				&& user.getPassword().equals(password)) {

			// TODO 此处把用户信息存入缓存 ：redis 或者memcache*/
			userService.set(user, JSESSIONID);
			userService.expire(JSESSIONID, Constants.EXPIRETIME);

			/*if (null != service) {
				 response.sendRedirect(service); //非json模式 
				 return ;
			} else {
				//跳转到用户中心系统 
				//response.sendRedirect("/index"); //非json模式
			}*/
			return "success";
		} else {
			/*
			 * // 直接在登录地址提示错误信息  
			 * response.sendRedirect(uriPrefix+"/login?"+request.getQueryString()); //非json模式
			 */
			return "error";
		}
	}
	
	
	/**
	 * 子域名单点登录
	 * 
	 * */
	@RequestMapping("/login/submit1")
	public void login1(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		String uriPrefix = request.getContextPath();
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String service = request.getParameter("service");
		String ticket = "";
		//TODO 此处用户名密码从缓存中获取，如果缓存中没有从关系型数据库获取
		User user = null ;
		Cookie[] cookies = request.getCookies(); // 读取cookie
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (Constants.JVMCACHE_KEY.equals(cookie.getName())) {
					ticket = cookie.getValue();
				}
			}
		}
		if(ticket != null && !"".equals(ticket))  user = userService.queryUserByKey(ticket);
		
		if(user == null){
			user = userService.queryUserByOther(username);
		}
		
		if (user !=null 
				&& user.getName().equals(username) 
				&& user.getPassword().equals(password)) {
			
			

			//TODO 此处把用户信息存入缓存 ：redis 或者memcache*/
			if(ticket == null || "".equals(ticket)) ticket = UUID.randomUUID().toString();
			
			Cookie cookie = new Cookie(Constants.JVMCACHE_KEY, ticket);
			cookie.setMaxAge(Constants.EXPIRETIME.intValue());
			cookie.setPath("/");
//			cookie.setHttpOnly(true);
			cookie.setDomain(".wjw.com");
			response.addCookie(cookie);
			
			System.out.println("ticket:> "+ticket);
			userService.set(user, ticket);
			userService.expire(ticket, Constants.EXPIRETIME);
			
	        
			if (null != service) {
				response.sendRedirect(service);
				return ;
			} else {
				//跳转到用户中心系统
				response.sendRedirect("/index");
			}
		} else {
			//直接在登录地址提示错误信息
			response.sendRedirect(uriPrefix+"/login?"+request.getQueryString());
		}
	}
}
