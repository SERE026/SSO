package com.bs.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bs.api.modle.Constants;
import com.bs.api.modle.User;
import com.bs.api.service.UserService;
import com.bs.service.util.JsonObjUtil;

@Controller
public class LoginController {
	
	
	@Autowired
	private UserService userService;

	@RequestMapping("/login")
	public String toLogin(HttpServletRequest request, HttpServletResponse response,Model model){
		
		model.addAttribute("service",request.getParameter("service"));
		return "login";
	}
	
	
	
	/**
	 * 完全跨域名单点登录
	 * 
	 * */
	@ResponseBody
	@RequestMapping("/login/submit")
	public String login(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		String jsonpCallback = request.getParameter("jsonpCallback");

		String JSESSIONID = request.getParameter("jsessionid");  //此处需要验签
		
		if(StringUtils.isBlank(JSESSIONID)) JSESSIONID = request.getSession().getId();
		/***
		 * 1. 此处用户名密码从缓存中获取， 2. 如果缓存中没有从关系型数据库获取
		 **/
		User user = null;
		if (StringUtils.isBlank(JSESSIONID))
			user = userService.queryUFRelationBySID(JSESSIONID); // 1.此处用户名密码从缓存中获取，

		if (user == null) {
			user = userService.queryUserByOther(username); // 2.缓存中没有从关系型数据库获取
		}

		if (user != null && user.getName().equals(username)
				&& user.getPassword().equals(password)) {

			// TODO 此处把用户信息存入缓存 ：redis 或者memcache*/
			userService.set(user, JSESSIONID);
			userService.expire(JSESSIONID, Constants.EXPIRETIME);

			return jsonpCallback+"("+loginJsonUrl("success",JSESSIONID)+")";
		} else {
			return jsonpCallback+"("+loginJsonUrl("error",JSESSIONID)+")";
		}
	}
	
	
	@RequestMapping("/account")
	public String account(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		return "user/account";
	}
	
	/**
	 * 查询用户信息，客户端拦截器使用
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	@ResponseBody
	@RequestMapping("/p/query")
	public String query(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		String jsessionId = request.getParameter(Constants.CACHE_COOKIE_KEY);
		
		User user = userService.queryUFRelationBySID(jsessionId);
		
		if(user != null) return JsonObjUtil.objToJson(user);
		else return null;
	}
	
	
	
	
	public String loginJsonUrl(String type,String JSESSIONID){
		List<String> list = new ArrayList<String>(1);
		JSONObject json = new JSONObject();
		// TODO 此处需要进行加密验签
		if("success".equals(type)){
			list.add("http://www.web1.com:9081/index");
			list.add("http://www.passport.com:9080/index");
			json.put("sso", list);
		}else{
			json.put("sso", "");
		}
		json.put("jsessionid", JSESSIONID);
		return json.toString();
	}
	
}
