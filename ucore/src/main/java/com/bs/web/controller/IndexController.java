package com.bs.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bs.web.interceptor.GlobalInterceptor;

@Controller
public class IndexController {
	
	private static Logger LOG = LoggerFactory.getLogger(IndexController.class);
	
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
		LOG.info("上一个地址来源：{}",referer);
		return "index";
	}

	@RequestMapping("/index")
	public String index1(HttpServletRequest request) {
		Object referer = request.getHeader("Referer");
		LOG.info("上一个地址来源：{}",referer);
		return "index";
	}
	
}
