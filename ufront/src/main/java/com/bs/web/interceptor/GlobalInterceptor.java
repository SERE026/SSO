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
		
		String JSESSIONID = request.getSession().getId();
		/**
		 *  缓存中查询
		 */
		User user = userService.queryUserByKey(JSESSIONID);
		
		if(uri.equals("/") || StringUtils.startsWith(uri,uriPrefix+"/js/") 
				|| StringUtils.startsWith(uri,uriPrefix+"/images/")
				|| StringUtils.startsWith(uri,uriPrefix+"/css/")
				|| StringUtils.startsWith(uri,uriPrefix+"/servlet/")
				|| StringUtils.startsWith(uri,uriPrefix+"/unck/")
				|| StringUtils.startsWith(uri,uriPrefix+"/login")
				|| StringUtils.startsWith(uri,uriPrefix+"/p")
				|| StringUtils.startsWith(uri,uriPrefix+"/register"))
		{
			LOG.info("不经过拦截器处理的url地址：{}",uri);
		}
		else
		{
			
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
				
				//response.sendRedirect(Global.getConfig(Constants.SSOURL)+"?service="+backurl);
				response.sendRedirect(uriPrefix+"/login");
				
				return false;
			}	
		}
		
		//  更新用户在缓存中的值
		if(user !=null ) userService.expire(JSESSIONID, Constants.EXPIRETIME);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		
	}
}
