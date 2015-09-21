package com.bs.web.controller;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	public static volatile Map<Object, Object> map = new ConcurrentHashMap<Object, Object>();

	public static Map<Object, Object> mapSrc = new ConcurrentHashMap<Object, Object>();
	static {
		mapSrc.put("wangjw", "123456");
		mapSrc.put("test", "123456");
		mapSrc.put("lisi", "123456");
		mapSrc.put("zhangsan", "123456");
	}

	/**
	 * 首页测试
	 */
	@RequestMapping("/")
	public String index(HttpServletRequest request) {
		Object referer = request.getHeader("Referer");
		Object loginUrl = request.getAttribute("loginUrl");
		request.setAttribute("loginUrl",loginUrl);
		request.setAttribute("Referer", referer);
		return "user/account";
	}

	@RequestMapping("/index")
	public String index1(HttpServletRequest request) {
		Object referer = request.getHeader("Referer");
		Object loginUrl = request.getAttribute("loginUrl");
		request.setAttribute("loginUrl",loginUrl);
		request.setAttribute("Referer", referer);
		return "user/account";
	}
	
}
