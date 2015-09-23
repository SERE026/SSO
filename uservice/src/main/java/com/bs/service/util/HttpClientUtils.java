package com.bs.service.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bs.api.modle.Constants;
import com.bs.api.modle.User;
 
public class HttpClientUtils {
	
	private static Logger LOG = LoggerFactory.getLogger(HttpClientUtils.class);
	
	
	private static HttpClientUtils httpClientUtils = new HttpClientUtils();
	
	
	public static synchronized HttpClientUtils getInstance(){
		return httpClientUtils;
	}
	
	
	/**
	 * 查询用户是否已经登录，通过sessionid 查询
	 * @param jsessionId
	 * @return
	 */
	public User isLogin(String jsessionId){
		try{
			// 创建默认的httpClient实例.    
	        HttpClient client = HttpClients.createDefault();  
	        // 创建httppost    
	        HttpPost httppost = new HttpPost(Global.getConfig(Constants.SSO_URL_QUERY));  
	        // 创建参数队列    
	        List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
	        formparams.add(new BasicNameValuePair(Constants.CACHE_COOKIE_KEY, jsessionId));  
	        
	        UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8"); 
	        
	        httppost.setEntity(uefEntity);  
	          
	        HttpResponse response = client.execute(httppost);  
	        HttpEntity entity = response.getEntity();  
	        User user = null;
	        if (entity != null) {  
	            LOG.info("--------------------------------------");  
	            user = (User) JsonObjUtil.stringToObj(EntityUtils.toString(entity, "UTF-8"), User.class);
	            LOG.info("Login Response content: " + ToStringBuilder.reflectionToString(user,ToStringStyle.MULTI_LINE_STYLE)); 
	            LOG.info("--------------------------------------");  
	        }
	        return user;
		}catch(Exception e){
			LOG.error("查询用户信息异常: "+e.getMessage());
			return null;
		}
	}
	
	
	 /**
	  * post方式提交 （模拟用户登录请求）   
	  * @param username
	  * @param pwd
	  * @param imgCode
	  * @return
	  */
    public Object postLogin(String username,String pwd,String imgCode) {
    	try {
	        // 创建默认的httpClient实例.    
	        HttpClient client = HttpClients.createDefault();  
	        // 创建httppost    
	        HttpPost httppost = new HttpPost(Global.getConfig(Constants.SSO_URL_LOGIN));  
	        // 创建参数队列    
	        List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
	        formparams.add(new BasicNameValuePair("username", username));  
	        formparams.add(new BasicNameValuePair("password", pwd));  
	        formparams.add(new BasicNameValuePair("imgcode", imgCode));  
	        
	        UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8"); 
	        
            httppost.setEntity(uefEntity);  
              
            HttpResponse response = client.execute(httppost);  
            HttpEntity entity = response.getEntity();  
            Object result = null;
            if (entity != null) {  
                LOG.info("--------------------------------------");  
                result = EntityUtils.toString(entity, "UTF-8");
                LOG.info("Login Response content: " + ToStringBuilder.reflectionToString(result,ToStringStyle.MULTI_LINE_STYLE)); 
                LOG.info("--------------------------------------");  
            }
            return result;
        } catch (IOException e) { 
        	LOG.error("执行登录异常：{}"+e.getMessage());
        	return null;
        } 
        
    }  
 
    
    
    
    
    public static void main(String args[]) throws IOException {
    }
}
