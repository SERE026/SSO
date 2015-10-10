package com.bs.web.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bs.api.service.SessionManagerService;


@Controller
public class IndexController {
	
	private static Logger LOG = LoggerFactory.getLogger(IndexController.class);
	
	public static volatile Map<Object, Object> map = new ConcurrentHashMap<Object, Object>();
	
	@Autowired
	private SessionManagerService sessionManagerService;

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
	public String index(HttpServletRequest request,HttpServletResponse response) {
		Object referer = request.getHeader("Referer");
		LOG.info("上一个地址来源：{}",referer);
		return "index";
	}

//	@ResponseBody
	@RequestMapping("/index")
	public String index1(HttpServletRequest request,HttpServletResponse response) {
		Object referer = request.getHeader("Referer");
		LOG.info("上一个地址来源：{}",referer);
		/*String JsessionId =  SessionUtil.getJSessionId(request);
	
		User user = SessionUtil.getUserFromSession(request, sessionManagerService, JsessionId);
		
		SessionUtil.setAttribute(request, JsessionId, user);
		
		String jsonpCallback = request.getParameter("jsonpCallback");*/
		
		return "index";
	}
	
}
