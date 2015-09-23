package com.bs.web.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bs.api.modle.User;
import com.bs.api.service.SessionManagerService;
import com.bs.web.util.SessionUtil;

@Controller
public class IndexController {

	private static Logger LOG = LoggerFactory.getLogger(IndexController.class);
	
	public static volatile Map<Object, Object> map = new ConcurrentHashMap<Object, Object>();
	
	@Autowired
	private SessionManagerService sessionManagerService;
	
	/**
	 * 首页测试
	 */
//	@ResponseBody
	@RequestMapping("/index")
	public String index(HttpServletRequest request){
		/*String JsessionId =  SessionUtil.getJSessionId(request);
		
		User user = SessionUtil.getUserFromSession(request, sessionManagerService, JsessionId);
		
		SessionUtil.setAttribute(request, JsessionId, user);
		
		String jsonpCallback = request.getParameter("jsonpCallback");*/
		
		return "index";
	}
	
	/**
	 * 首页测试
	 */
	@RequestMapping("/")
	public String first(HttpServletRequest request){
		return "index";
	}
	
}
