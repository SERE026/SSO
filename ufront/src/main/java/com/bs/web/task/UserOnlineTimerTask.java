package com.bs.web.task;

import java.util.TimerTask;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bs.api.modle.UConstants;
import com.bs.api.modle.User;
import com.bs.api.service.SessionManagerService;
import com.bs.service.util.SpringContextHolder;
import com.bs.web.listener.SessionJobListener;
import com.bs.web.util.SessionUtil;

public class UserOnlineTimerTask extends TimerTask{
	
	private SessionManagerService sessionManagerService = SpringContextHolder.getBean(SessionManagerService.class);
	
	private static Logger LOG = LoggerFactory.getLogger(SessionJobListener.class);
	
	public UserOnlineTimerTask(){
		super();
	}
	
	
	public UserOnlineTimerTask(HttpSessionEvent e) {
		super();
		this.event = e;
	}

	
	private HttpSessionEvent event;

	/**
	 * 此任务 不断的查询Session 不断的更新Session在用户中心的时间
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		HttpSession session = event.getSession();
		
		LOG.info("FRONT_UserOnline_s_t的内容：{}",session.getLastAccessedTime());
		LOG.info("FRONT_UserOnline_s_t_m的内容：{}",session.getMaxInactiveInterval());
		LOG.info("FRONT_UserOnline_s_id的内容：{}",session.getId());
		
		
		User user = (User) session.getAttribute(UConstants.CACHE_COOKIE_KEY);
		if(user!=null){
			// 此处可以得到时间戳
			LOG.info("FRONT_UserOnline_s_v的内容：{}",user.toString());
			//sessionManagerService.queryUserBySID(UConstants.CACHE_COOKIE_KEY,user.getName());
			
			String key = UConstants.CACHE_COOKIE_KEY + user.getName();
			
			sessionManagerService.expireAt(key, session.getLastAccessedTime());
		}
		
		
		//sessionManagerService.expire(event.getSession().get, time);
	}


	public HttpSessionEvent getEvent() {
		return event;
	}

	public void setEvent(HttpSessionEvent event) {
		this.event = event;
	}

}
