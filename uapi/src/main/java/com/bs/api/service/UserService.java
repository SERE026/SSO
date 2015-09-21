package com.bs.api.service;

import com.bs.api.modle.User;

public interface UserService {

	/**
	 * 根据key值取用户在缓存中的值
	 * @param sessionId
	 * @return
	 */
	public User queryUserBySessionId(String sessionId);
	
	/**
	 * 保存用户信息 
	 * @param user <br> 用户信息
	 * @param key  <br> key=null 存入mysql,
	 * 			   <br> key!=null 存入缓存 ，
	 * @return
	 */
	public void save(User user,String key);
	
	/**
	 * 删除缓存中的信息
	 * @param key
	 * @return 
	 */
	public Object delete(String key);
	
	
	/**
	 * 根据key值取用户在缓存中的值
	 * @param sessionId
	 * @return
	 */
	public User queryUserByKey(String key);
	
	/**
	 * 保存用户信息 
	 * @param user <br> 用户信息
	 * @param key  <br> key=null 存入mysql,
	 * 			   <br> key!=null 存入缓存 ，
	 * @return
	 */
	public void set(User user,String key);
	
	/**
	 * 删除缓存中的信息
	 * @param key
	 * @return 
	 */
	public void del(String key);
	
	/**
	 * key 的过期时间设置
	 * @param key
	 * @param time
	 */
	public void expire(String key,Long time);

	/**
	 * 从关系型持久层数据库取数据,根据邮箱、手机、昵称、真实姓名 且正常的用户
	 * @param username
	 * @return
	 */
	public User queryUserByOther(String username);
}
