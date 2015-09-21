package com.bs.web.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;

import com.bs.api.modle.Constants;
import com.bs.api.modle.User;
import com.bs.api.service.UserService;
import com.bs.service.util.Global;
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
			
			User user = userService.queryUserByKey(tkey);
			String username = user.getName();
			String url = Global.getConfig(Constants.SSOURL);
			
			//url重定向
			Map<String, String[]> params = request.getParameterMap();  
			for (String key : params.keySet()) {  
			    String[] values = params.get(key);  
			    for (int i = 0; i < values.length; i++) {  
			        String value = values[i];  
			        if(StringUtils.isNotBlank(value)){
			        	request.setAttribute(key, value);
			        }
			    }  
			    
			}  
			request.getRequestDispatcher(url).forward(request, response);
			return false;
		}
		
		//TODO 更新用户在缓存中的值
		userService.expire(tkey, Constants.EXPIRETIME);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		
	}
}
