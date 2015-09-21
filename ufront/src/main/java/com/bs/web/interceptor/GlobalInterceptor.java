package com.bs.web.interceptor;

import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.bs.api.modle.Constants;
import com.bs.api.modle.User;
import com.bs.api.service.UserService;
import com.bs.service.util.Global;
import com.bs.service.util.HttpClientUtils;
import com.bs.service.util.SpringContextHolder;


public class GlobalInterceptor  implements HandlerInterceptor {

	private static Logger LOG = LoggerFactory.getLogger(GlobalInterceptor.class);
	private UserService userService = SpringContextHolder.getBean(UserService.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		response.setHeader("progma","no-cache");  
	    response.setHeader("Cache-Control","no-cache");  
	    response.setDateHeader("Expires",0);
		
		String uri = request.getRequestURI();
		String uriPrefix = request.getContextPath();
		
		String tkey = request.getParameter("ticket");
		
		Cookie[] cookies = request.getCookies(); // 读取cookie
		String tmpkey = "";
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (Constants.JVMCACHE_KEY.equals(cookie.getName())) {
					tmpkey = cookie.getValue();
					cookie.setMaxAge(Constants.EXPIRETIME.intValue());
//					cookie.setHttpOnly(true);
					cookie.setPath("/");
					response.addCookie(cookie);
				}
			}
		}
		
		if(!StringUtils.isNotBlank(tkey) && StringUtils.isNotBlank(tmpkey)){
			tkey = tmpkey;
			Cookie cookie = new Cookie(Constants.JVMCACHE_KEY,tkey);
			cookie.setMaxAge(Constants.EXPIRETIME.intValue());
//			cookie.setHttpOnly(true);
			cookie.setPath("/");
			response.addCookie(cookie);
		}
		
		if(StringUtils.isNotBlank(tkey) ){
			Cookie cookie = new Cookie(Constants.JVMCACHE_KEY,tkey);
			cookie.setMaxAge(Constants.EXPIRETIME.intValue());
//				cookie.setHttpOnly(true);
			cookie.setPath("/");
			response.addCookie(cookie);
			
			/*//同步到用户中心cookie
			HttpClient client = HttpClientUtils.getConnection();
			Map map = new ConcurrentHashMap();
			map.put(Constants.JVMCACHE_KEY, tkey);
	        HttpUriRequest post = 	HttpClientUtils.getRequestMethod1(map, "http://www.web2.com:9080/ticket", "get");
//	        post.setHeader(Constants.JVMCACHE_KEY, tkey);
	        HttpResponse resp = client.execute(post);
	        
	        System.out.println(resp.getStatusLine().getStatusCode());
	        if(resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
	        	System.out.println("OK");
	        }else{
	        	System.out.println("失败");
	        }*/
		}
		
		if(uri.equals("/") || StringUtils.startsWith(uri,uriPrefix+"/js/") 
				|| StringUtils.startsWith(uri,uriPrefix+"/images/")
				|| StringUtils.startsWith(uri,uriPrefix+"/css/")
				|| StringUtils.startsWith(uri,uriPrefix+"/servlet/")
				|| StringUtils.startsWith(uri,uriPrefix+"/unck/")
				|| StringUtils.startsWith(uri,uriPrefix+"/login")
				|| StringUtils.startsWith(uri,uriPrefix+"/p")
				|| StringUtils.startsWith(uri,uriPrefix+"/register"))
		{
			
		}
		else
		{
			
			
			User user = null ;
			if(tkey != null && !"".equals(tkey)) user = userService.queryUserByKey(tkey);
			
			if(user ==null)
			{

				
				LOG.info("请求参数：>>> "+request.getQueryString());
		        Map<String, String[]> params = request.getParameterMap();  
		        String queryString = "?";  
		        for (String key : params.keySet()) {  
		            String[] values = params.get(key);  
		            for (int i = 0; i < values.length; i++) {  
		                String value = values[i];  
		                queryString += key + "=" + value + "&";  
		            }  
		        }  
		        // 去掉最后一个空格  
		        queryString = queryString.substring(0, queryString.length() - 1);  
				
				StringBuffer all=request.getRequestURL();
				int index=all.indexOf(uri);
				String backurl=request.getRequestURI();
				//回调地址
				backurl = all.substring(0,index)+backurl+queryString;
				backurl=URLEncoder.encode(backurl, "utf-8");
				
				response.sendRedirect(Global.getConfig(Constants.SSOURL)+"?service="+backurl);
				
				return false;
			}	
		}
		
		//TODO 更新用户在缓存中的值
		if(StringUtils.isNotBlank(tkey)) userService.expire(tkey, Constants.EXPIRETIME);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		
	}
}
