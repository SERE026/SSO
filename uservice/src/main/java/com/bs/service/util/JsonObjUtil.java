package com.bs.service.util;

import net.sf.json.JSONObject;

import com.bs.api.modle.User;

public class JsonObjUtil {
	
	
	public static String objToJson(Object obj){
		return new JSONObject().fromObject(obj).toString();
	}
	
	
	public static Object jsonToObj(JSONObject json,Class beanClass){
		return JSONObject.toBean(json, beanClass);
	}
	
	public static Object stringToObj(String json,Class beanClass){
		JSONObject jsonObj = JSONObject.fromObject(json);
		return JSONObject.toBean(jsonObj, beanClass);
	}
	
	public static void main(String[] args) {
		
		String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new java.util.Date(1444378163404L));  
		System.out.println(date);
		
		User u = new User();
		u.setName("aaaa");
		u.setPassword("bbbbpwd");
		u.setAge(20);
		u.setSex(1);
		u.setJob("IT");
		u.setUniversity("ALIBABA UNIVERSITY");
		
		String jsonvalue= objToJson(u);
		System.out.println(jsonvalue);
		
		User ut = (User) stringToObj(jsonvalue, User.class);
		
		System.out.println(ut);
	}
}
