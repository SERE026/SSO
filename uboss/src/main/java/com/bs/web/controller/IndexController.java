package com.bs.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	/**
	 * 首页测试
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request){
		return "index";
	}
	
	
}
