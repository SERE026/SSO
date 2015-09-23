package com.bs.service.impl;

import java.text.ParseException;
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
import com.bs.service.util.HttpClientUtils;


@Service("com.bs.api.service.SessionManagerService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
public class SessionManagerServiceImpl implements SessionManagerService {

	@Autowired
	private RedisTemplate<String, User> redisTemplate;
	
	@Override
	public void removeSession(String sessionId) {
		// TODO Auto-generated method stub

	}

	@Override
	public User queryUserBySID(String sessionId) {
		return HttpClientUtils.getInstance().isLogin(sessionId);
	}

	@Override
	public void expire(String key, Long time) {
		redisTemplate.expire(key, time, TimeUnit.SECONDS);
//		redisTemplate.expireAt(key, fmtDate(time));
	}

	private Date fmtDate(Long time) {
		String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new java.util.Date(time));
		Date d = null;
		try {
			d = new java.text.SimpleDateFormat().parse(date);
		} catch (ParseException e) {
			d = new Date();
			e.getMessage();
		}
		return d;
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
		// TODO Auto-generated method stub
		redisTemplate.opsForValue().set(key, user);
		redisTemplate.expire(key, UConstants.EXPIRETIME, TimeUnit.SECONDS);
	}

}
