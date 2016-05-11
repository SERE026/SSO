package com.bs.service;

import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * AOP面向切面的使用
 * @author sere
 *
 */
@Service
@Aspect
public class AspectService {
	
	private static Logger LOG = LoggerFactory.getLogger(AspectService.class);

	 /** 
     * 定义Pointcut，Pointcut的名称 就是simplePointcut，此方法不能有返回值，该方法只是一个标示 
     * execution(* com.ycm.third.sina.SinaSendUtil.sendRequest(..)) 具体到类方法
     */  
    @Pointcut("execution(* com.bs..*.*(..)) ")  
    public void point() {  
    }  
    
    /**
     * 前置通知，放在方法头上。
     * args 获取执行方法的参数
     */
    @Before("point() && args(map,type)")  
    public void before(JoinPoint jp,Map map,Integer type) {  
        String className = jp.getThis().toString();  
        String methodName = jp.getSignature().getName(); // 获得方法名  
        LOG.info("位于：" + className + "调用" + methodName + "()方法-开始！");  
        Object[] args = jp.getArgs(); // 获得参数列表  
        if (args.length <= 0) {  
            LOG.info("====" + methodName + "方法没有参数");  
        } else {  
            for (int i = 0; i < args.length; i++) {  
                LOG.info("====参数  " + (i + 1) + "：" + args[i]);  
            }  
        }  
        LOG.info("=====================================");  
    }  
    
    /**
     * 后置【finally】通知，放在方法头上。
     * @param jp
     */
    @After("point()")  
    public void after(JoinPoint jp) {  
        LOG.info("" + jp.getSignature().getName() + "()方法-结束！");  
        LOG.info("=====================================");  
    }
    
    /**
     * 后置【try】通知，放在方法头上，
     * 使用returning来引用方法返回值。
     * 
     */
    @AfterReturning(pointcut = "point()",returning = "result")  
    public void afterReturning(JoinPoint jp,Object result) {  
        LOG.info("AOP后处理成功 ");  
    }  
  
    /**
     * 后置【catch】通知，放在方法头上，使用throwing来引用抛出的异常。
     * @param jp
     * @return
     * @throws Throwable
     */
    @AfterThrowing("point()")  
    public void catchInfo() {  
    	LOG.info("异常信息");  
    } 
    
    /**
     * 环绕通知，放在方法头上，这个方法要决定真实的方法是否执行，而且必须有返回值。
     * @param jp
     * @return
     * @throws Throwable
     */
    @Around("point()")  
    public Object around(ProceedingJoinPoint jp) throws Throwable {  
        LOG.info("正常运行");  
        return jp.proceed();  
    }  
  
   
}
