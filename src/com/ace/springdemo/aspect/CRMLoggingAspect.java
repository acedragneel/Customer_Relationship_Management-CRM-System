package com.ace.springdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.mysql.cj.protocol.Message;
import com.sun.istack.internal.logging.Logger;

@Aspect
@Component
public class CRMLoggingAspect {

	//setup Logger 
	private Logger myLogger = Logger.getLogger(getClass());
	
	//Setup pointcut Declarations
	@Pointcut("execution(* com.ace.springdemo.controller.*.*(..))")
	private void forControllerPackage(){}
	
	@Pointcut("execution(* com.ace.springdemo.service.*.*(..))")
	private void forServicePackage(){}
	
	@Pointcut("execution(* com.ace.springdemo.dao.*.*(..))")
	private void forDAOPackage(){}
	
	@Pointcut("forControllerPackage() || forServicePackage() || forDAOPackage()")
	private void forAppFlow(){}
	
	
	@Before("forAppFlow()")
	public void before(JoinPoint theJoinPoint)
	{
		//display the method we are calling
		String theMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("====>> in @Before: calling method:" + theMethod);
		
		// get the arguments
		Object[] theArgs = theJoinPoint.getArgs();
		
		for (Object object : theArgs) {
			myLogger.info("======> Arguments "+ object);
		}
		
	}
	
	@AfterReturning(
			pointcut="forAppFlow()",
			returning="theResult")
	public void after(JoinPoint theJoinPoint, Object theResult)
	{
		//display the method we are calling
		String theMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("====>> in @AfterReturing: calling method:" + theMethod);
		
		myLogger.info("=====>> Result:"+ theResult);
	}
}
