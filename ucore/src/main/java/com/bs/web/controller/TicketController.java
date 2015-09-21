package com.bs.web.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.Cookies;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bs.api.modle.Constants;

@Controller
public class TicketController {

	public static volatile Map<Object, Object> map = new ConcurrentHashMap<Object, Object>();

	public static Map<Object, Object> mapSrc = new ConcurrentHashMap<Object, Object>();
	static {
		mapSrc.put("wangjw", "123456");
		mapSrc.put("test", "123456");
		mapSrc.put("lisi", "123456");
		mapSrc.put("zhangsan", "123456");
	}


	@ResponseBody
	@RequestMapping("/ticket")
	public String index1(HttpServletRequest request ,HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		System.out.println("header:>=="+request.getHeaders("ticket"));
		String tmp = "1";
		for(int i = 0;i<cookies.length;++i){
			System.out.println(cookies[i].getName()+" ==== "+cookies[i].getValue());
			if(cookies[i].getName().equals(Constants.JVMCACHE_KEY)){
				tmp = cookies[i].getValue() ;
			}
		}
		return  tmp;
	}
	
}
