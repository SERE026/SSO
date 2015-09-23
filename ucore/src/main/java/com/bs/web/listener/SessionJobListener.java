package com.bs.web.listener;

import java.io.IOException;
import java.util.Timer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.catalina.Context;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bs.api.modle.UConstants;
import com.bs.web.task.UserOnlineTimerTask;

public class SessionJobListener extends HttpServlet implements ServletContextListener,HttpSessionListener {

	private static final long serialVersionUID = 1L;
	
	private static Logger LOG = LoggerFactory.getLogger(SessionJobListener.class);
	
	
	Timer timer = new Timer();  

	@Override
	public void service(ServletRequest request, ServletResponse response)  
			throws ServletException, IOException {  
		// TODO Auto-generated method stub
		
	}  

	@Override
	public void contextInitialized(ServletContextEvent e) {
		// TODO Auto-generated method stub
		LOG.info("initial context....");  
		
		LOG.info("监听器输出的内容：{}",ToStringBuilder.reflectionToString(e.getServletContext().getAttribute(UConstants.CACHE_COOKIE_KEY)));
		LOG.info("监听器输出的内容：{}",ToStringBuilder.reflectionToString(e.getServletContext().getAttributeNames()));
		
		//timer.schedule(new UserOnlineTimerTask(), 0, 10000);  
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent e) {
		// TODO Auto-generated method stub
		LOG.info("destory context....");  
		  
		timer.cancel();  
	}

	@Override
	public void sessionCreated(HttpSessionEvent e) {
		// TODO Auto-generated method stub
		LOG.info("initial context....");  
		
		LOG.info("监听器输出的内容：{}",ToStringBuilder.reflectionToString(e.getSession().getServletContext().getAttribute(UConstants.CACHE_COOKIE_KEY)));
		LOG.info("监听器输出的内容：{}",ToStringBuilder.reflectionToString(e.getSession().getServletContext().getAttributeNames()));
		
		timer.schedule(new UserOnlineTimerTask(e), 0, 50000);  
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent e) {
		// TODO Auto-generated method stub
		
	}

	

	

	
}
