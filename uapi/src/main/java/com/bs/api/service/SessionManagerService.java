package com.bs.api.service;

import com.bs.api.modle.User;

public interface SessionManagerService {

	/**
	 *  创建session
	 */
	public void createSession();
	
	/**
	 * 移除session
	 * @param sessionId
	 */
	public void removeSession(String sessionId);
	
	/**
	 * 
	 */
	public User queryUserBySID();
	
}
