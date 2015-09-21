package com.bs.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	@ResponseBody 
	@RequestMapping("/p/queryListUrl")
	public String queryListUrl(HttpServletRequest request,HttpServletResponse response) {
		List<String> list = new ArrayList<String>(1);
		list.add("www.web1.com");
//		list.add("www.web2.com");
		list.add("www.passport.com");
		JSONObject json = new JSONObject();
		json.put("listUrl", list);
		return json.toString();
	}
	
}
