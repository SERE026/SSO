package com.bs.web.listener;

import java.io.IOException;
import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bs.api.model.UConstants;
import com.bs.web.task.UserOnlineTimerTask;

public class SessionJobListener extends HttpServlet implements ServletContextListener,HttpSessionAttributeListener {

	private static final long serialVersionUID = 1L;
	
	private static Logger LOG = LoggerFactory.getLogger(SessionJobListener.class);
	
	
	Timer timer = new Timer();  


	@Override
	public void contextInitialized(ServletContextEvent e) {
		LOG.info("initial context....");  
		
		LOG.info("监听器输出的内容：{}",ToStringBuilder.reflectionToString(e.getServletContext().getAttribute(UConstants.CACHE_COOKIE_KEY)));
		LOG.info("监听器输出的内容：{}",ToStringBuilder.reflectionToString(e.getServletContext().getAttributeNames()));
		
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent e) {
		LOG.info("destory context....");  
		  
		timer.cancel();  
	}

	@Override
	public void attributeAdded(HttpSessionBindingEvent e) {
		LOG.info("FRONT_监听器输出的内容：{}",e.getSession().getAttribute(e.getSession().getId()));
		LOG.info("FRONT_监听器输出的内容：{}",ToStringBuilder.reflectionToString(e.getSession().getAttributeNames()));
		timer.schedule(new UserOnlineTimerTask(e), 0, 50000); 
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent e) {
		
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent e) {
		
	}


	
}
