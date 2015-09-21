package com.bs.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bs.api.modle.Constants;
import com.bs.api.modle.User;
import com.bs.api.service.UserService;

@Controller
public class LoginController {

	@Autowired
	private UserService userService ;
	
	@RequestMapping("/login")
	public String login(HttpServletRequest request,HttpServletResponse response,Model model){
		 
		return "login";
	}
	
	
	
	@ResponseBody
	@RequestMapping("/user/list")
	public String  test(HttpServletRequest request,HttpServletResponse response,Model model){
		
		
		return "";
	}
	
	@RequestMapping("/account")
	public String account(HttpServletRequest request,HttpServletResponse response,Model model){
		 
		return "account/account";
	}
}
