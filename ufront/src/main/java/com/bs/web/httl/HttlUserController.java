package com.bs.web.httl;

import httl.Engine;
import httl.Template;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bs.api.model.User;
import com.bs.api.service.SessionManagerService;
import com.bs.api.service.UserService;

@Controller
public class HttlUserController {

	private static Logger LOG = LoggerFactory.getLogger(HttlUserController.class);
	
	public static volatile Map<Object, Object> map = new ConcurrentHashMap<Object, Object>();
	
	@Autowired
	private UserService userService;
	
	/**
	 * 首页测试
	 * @throws ParseException 
	 * @throws IOException 
	 */
	@RequestMapping("/p/users")
	public String book(HttpServletRequest request,HttpServletResponse response) throws IOException, ParseException{
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		User user = userService.queryUserByOther("");
		parameters.put("user", user);
		 
		Engine engine = Engine.getEngine();
		Template template = engine.getTemplate("/user.httl");
		template.render(parameters, response.getOutputStream());
		return "index";
	}
	
	
}
