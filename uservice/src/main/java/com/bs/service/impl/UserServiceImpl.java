package com.bs.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bs.api.model.User;
import com.bs.api.service.UserService;
import com.bs.service.util.HttpClientUtils;
import com.bs.service.util.JsonObjUtil;

@Service("com.bs.api.service.UserService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
public class UserServiceImpl implements UserService{
	
	
	@Autowired
	private RedisTemplate<String, User> redisTemplate;

	@Override
	public User queryUserBySessionId(final String key) {
		
		User userOut = redisTemplate.execute(new RedisCallback<User>() {

			@Override
			public User doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] s_key = redisTemplate.getStringSerializer().serialize(key);  
				byte[] value = connection.get(s_key);
				String jsonUser = (String) redisTemplate.getStringSerializer().deserialize(value);
				User user = (User) JsonObjUtil.stringToObj(jsonUser, User.class);
				
	            return user;  
			}
			
		});
		return userOut;
	}
	

	@Override
	public void save(User user, final String key) {
		final String ustring = JsonObjUtil.objToJson(user);
		System.out.println(key+":>"+ustring);
		redisTemplate.execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection)
					throws DataAccessException {
				
				 connection.set(  
                    redisTemplate.getStringSerializer().serialize(  
                            key),  
                    redisTemplate.getStringSerializer().serialize(  
                            ustring));
				 
				return null;
			}
			
		});
	}

	@Override
	public Object delete(final String key) {
		return redisTemplate.execute(new RedisCallback<Object>() {  
	        public Object doInRedis(RedisConnection connection) {  
	            Long l = connection.del(redisTemplate.getStringSerializer().serialize(key));  
	            return l;  
	        }  
	    });  
	}

	@Override
	public User queryUserByKey(String key) {
		User user = null;
		Object obj = redisTemplate.opsForValue().get(key);
		if(obj!=null) user = (User)obj;
		return user;
	}
	
	@Override
	public User queryUFRelationBySID(String key,String name) {
		return HttpClientUtils.getInstance().isLogin(key,name);
	}

	@Override
	public void set(User user, String key) {
		redisTemplate.opsForValue().set(key, user);
	}

	@Override
	public void del(String key) {
		redisTemplate.delete(key);
	}

	@Override
	public void expire(String key, Long time) {
		redisTemplate.expire(key, time, TimeUnit.SECONDS);
	}

	@Override
	public User queryUserByOther(String username) {
		// TODO Auto-generated method stub
		return new User("cloud","clouod",20,1,"JUST DO IT","ALIBABA");
	}

	

}
