package com.bs.api.service;

import java.util.Date;

import com.bs.api.model.User;

public interface SessionManagerService {

	
	/**
	 * 移除session
	 * @param key
	 */
	public void removeSession(String key);
	
	/**
	 * 查询用户信息，根据key查询
	 * @param key
	 * @return
	 */
	public User queryUserBySID(String key,String name);
	
	/**
	 * 查询用户信息，根据用户名称查询 （从用户中心数据库查询）
	 * @param username
	 * @return
	 */
	public User queryUserByUsername(String username);
	
	/**
	 * 查询用户信息，根据用户手机号查询 （从用户中心数据库查询）
	 * @param mobile
	 * @return
	 */
	public User queryUserByMobile(String mobile);
	
	/**
	 * 查询用户信息，根据用户邮箱查询 （从用户中心数据库查询）
	 * @param email
	 * @return
	 */
	public User queryUserByEmail(String email);
	
	/**
	 * 保存用户信息到用户中心
	 * @param user
	 * @param key
	 */
	public void saveToUCore(User user,String key);
	
	/**
	 * 更新存活时间
	 * @param key
	 * @param time
	 */
	public void expire(String key, Long time);
	

	/**
	 * 更新存活时间
	 * @param key
	 * @param time
	 */
	public void expireAt(String key, Date time);
	
	/**
	 * 更新存活时间
	 * @param key
	 * @param time
	 */
	public void expireAt(String key, Long time);
	
}
