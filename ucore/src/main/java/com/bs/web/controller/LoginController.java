package com.bs.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bs.api.modle.UConstants;
import com.bs.api.modle.User;
import com.bs.api.service.SessionManagerService;
import com.bs.api.service.UserService;
import com.bs.service.util.DateUtil;
import com.bs.service.util.JsonObjUtil;
import com.bs.web.util.SessionUtil;

@Controller
public class LoginController {
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SessionManagerService sessionManagerService;
	
	/**
	 * 设置session
	 * JSONP 调用
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/setKey")
	public String setKey(HttpServletRequest request){
		String sign =  request.getParameter(UConstants.LOGIN_SIGN); //验签
		
		User user = SessionUtil.getUserFromSession(request, sessionManagerService);
		
		SessionUtil.setAttribute(request, UConstants.CACHE_COOKIE_KEY, user);
		
		String jsonpCallback = request.getParameter("jsonpCallback");
		
		return jsonpCallback+"({\"result\":\"ok\"})";
	}

	@RequestMapping("/login")
	public String toLogin(HttpServletRequest request, HttpServletResponse response,Model model){
		
		model.addAttribute("service",request.getParameter("service"));
		return "login";
	}
	
	
	
	/**
	 * 完全跨域名单点登录
	 * jsonp 调用
	 * */
	@ResponseBody
	@RequestMapping("/login/submit")
	public String login(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		String service = request.getHeader("Referer");
		String jsonpCallback = request.getParameter("jsonpCallback");

		String sign = request.getParameter(UConstants.LOGIN_SIGN);  //此处需要验签
		
		User user = sessionManagerService.queryUserByUsername(username); 

		if (user != null && user.getName().equals(username)
				&& user.getPassword().equals(password)) {

			// TODO 此处把用户信息存入用户中心  -->缓存 ：redis 或者memcache 或者mysql */
			// 生成key的规则(待定)
			String key = UConstants.CACHE_COOKIE_KEY+username; 
			sessionManagerService.saveToUCore(user, key);
			//放入session
			SessionUtil.setAttribute(request, UConstants.CACHE_COOKIE_KEY, user);

			return jsonpCallback+"("+loginJsonUrl("success",sign,username)+")";
		} else {
			return jsonpCallback+"("+loginJsonUrl("error",sign,username)+")";
		}
	}
	
	
	@RequestMapping("/account")
	public String account(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		/*try {
		 Field requestField;
			requestField = request.getClass().getDeclaredField("request");
			requestField.setAccessible(true);
			Request req = (Request) requestField.get(request);
			org.apache.catalina.Context context = req.getContext();
			Manager manager = context.getManager();
			//activeSessions = manager.getActiveSessions();
			
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
		return "user/account";
	}
	
	/**
	 * 服务端和客户端监听器中的调用
	 * 更新cache用户信息
	 * @param request
	 */
	@ResponseBody
	@RequestMapping("/expire")
	public void expire(HttpServletRequest request){
		
		String name = request.getParameter(UConstants.USER_NAME);
		String dt = request.getParameter(UConstants.EXPIRE_TIME);
		
		Date time = DateUtil.parseDate(dt);
		String key = UConstants.CACHE_COOKIE_KEY+name;
		sessionManagerService.expireAt(key, time);
		
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
		
		//TODO 验签(未完成)
		String sign = request.getParameter(UConstants.LOGIN_SIGN);
		
		
		String name = request.getParameter(UConstants.USER_NAME);
		
		//生成key的规则 (待定)
		String key = UConstants.CACHE_COOKIE_KEY+name;
		
		User user = userService.queryUserByKey(key);
		
		if(user != null) return JsonObjUtil.objToJson(user);
		else return null;
	}
	
	
	public String loginJsonUrl(String type,String sign,String name){
		List<String> list = new ArrayList<String>(1);
		JSONObject json = new JSONObject();
		// TODO 此处需要进行加密验签
		if("success".equals(type)){
			list.add("http://www.web1.com:9081/setKey");
			list.add("http://www.passport.com:9080/setKey");
			json.put("sso", list);
		}else{
			json.put("sso", "");
		}
		json.put(UConstants.LOGIN_SIGN, sign);
		json.put(UConstants.USER_NAME, name);
		return json.toString();
	}
	
}
