package com.bs.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bs.api.modle.Constants;
import com.bs.api.modle.User;
import com.bs.api.service.UserService;

@Controller
public class LoginController {

	@Autowired
	private UserService userService ;
	
	@RequestMapping("/p/login")
	public String login(HttpServletRequest request,HttpServletResponse response,Model model){
		
		/*String username = request.getParameter("username");
		String password = request.getParameter("password");
		String ticket = request.getParameter(Constants.JVMCACHE_KEY);
		String jsessionId = request.getParameter("jsessionid");
		
		if(username == null || "".equals(username) 
				|| password == null || "".equals(password)){
			model.addAttribute("message","用户名或者密码为空，请输入！");
			return "login";
		}
		if(ticket == null || "".equals(ticket)) ticket = jsessionId;
		User user = userService.queryUserByKey(ticket);
		
		if(user == null )  user = userService.queryUserByOther(username);
		
		*/
		 
		return "index";
	}
}
