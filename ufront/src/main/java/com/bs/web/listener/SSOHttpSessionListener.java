package com.bs.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ContextLoaderListener;

import com.bs.service.util.SpringContextHolder;

public class SSOHttpSessionListener extends ContextLoaderListener implements HttpSessionListener {

	
	@Override
	protected void configureAndRefreshWebApplicationContext(
			ConfigurableWebApplicationContext wac, ServletContext sc) {
		// TODO Auto-generated method stub
		super.configureAndRefreshWebApplicationContext(wac, sc);
	}
	
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		// TODO Auto-generated method stub
		
	}

}
