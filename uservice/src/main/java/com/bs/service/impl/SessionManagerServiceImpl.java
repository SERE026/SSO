package com.bs.service.impl;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bs.api.modle.UConstants;
import com.bs.api.modle.User;
import com.bs.api.service.SessionManagerService;
import com.bs.service.util.DateUtil;
import com.bs.service.util.HttpClientUtils;


@Service("com.bs.api.service.SessionManagerService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
public class SessionManagerServiceImpl implements SessionManagerService {

	@Autowired
	private RedisTemplate<String, User> redisTemplate;
	
	@Override
	public void removeSession(String key) {
		// TODO Auto-generated method stub

	}

	@Override
	public synchronized User queryUserBySID(String key,String name) {
		return HttpClientUtils.getInstance().isLogin(key,name);
	}

	@Override
	public void expire(String key, Long time) {
		redisTemplate.expire(key, time, TimeUnit.SECONDS);
	}

	@Override
	public User queryUserByUsername(String username) {
		// TODO 从用户中心数据库查询
		return new User("cloud","clouod",20,1,"JUST DO IT","ALIBABA");
	}

	@Override
	public User queryUserByMobile(String mobile) {
		// TODO Auto-generated method stub
		return new User("cloud","clouod",20,1,"JUST DO IT","ALIBABA");
	}

	@Override
	public User queryUserByEmail(String email) {
		// TODO Auto-generated method stub
		return new User("cloud","clouod",20,1,"JUST DO IT","ALIBABA");
	}

	@Override
	public void saveToUCore(User user,String key) {
		redisTemplate.opsForValue().set(key, user);
		redisTemplate.expire(key, UConstants.EXPIRETIME, TimeUnit.SECONDS);
	}

	@Override
	public void expireAt(String key, Date time) {
		redisTemplate.expireAt(key, time);
	}

	@Override
	public void expireAt(String key, Long time) {
		redisTemplate.expireAt(key, DateUtil.parseDate(time));
	}

}
