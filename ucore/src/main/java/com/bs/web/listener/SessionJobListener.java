package com.bs.web.listener;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

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
	public void attributeAdded(HttpSessionBindingEvent e) {
		LOG.info("CORE监听器输出的内容：{}",e.getSession().getAttribute(e.getSession().getId()));
		LOG.info("CORE监听器输出的内容：{}",ToStringBuilder.reflectionToString(e.getSession().getAttributeNames()));
		timer.schedule(new UserOnlineTimerTask(e), 0, 60000); 
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent e) {
		
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent e) {
		
	}


	
}
