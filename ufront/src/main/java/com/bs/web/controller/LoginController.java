package com.bs.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bs.api.modle.UConstants;
import com.bs.api.modle.User;
import com.bs.api.service.SessionManagerService;
import com.bs.api.service.UserService;
import com.bs.web.util.SessionUtil;

@Controller
public class LoginController {

	@Autowired
	private UserService userService ;
	
	@Autowired
	private SessionManagerService sessionManagerService;
	
	/**
	 * 设置session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/setKey")
	public String setKey(HttpServletRequest request){
		
		String sign = request.getParameter(UConstants.LOGIN_SIGN); //TODO
		
		User user = SessionUtil.getUserFromSession(request, sessionManagerService);
		
		SessionUtil.setAttribute(request, UConstants.CACHE_COOKIE_KEY, user);
		
		String jsonpCallback = request.getParameter("jsonpCallback");
		
		return jsonpCallback+"({\"result\":\"ok\"})";
	}
	
	
	@RequestMapping("/login")
	public String login(HttpServletRequest request,HttpServletResponse response,Model model){
		 
		return "login";
	}
	
	
	@RequestMapping("/account")
	public String account(HttpServletRequest request,HttpServletResponse response,Model model){
		 
		return "account/account";
	}
}
