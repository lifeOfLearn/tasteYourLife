package iot.tyl.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import iot.tyl.exception.TylBaseException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Async("logPool")
@Aspect
@Component
public class LogAspect {

	@Pointcut("execution(* iot.tyl.controller..*(..)) || execution(* iot.tyl.service.impl..*(..)) || execution(* iot.tyl.interceptor..*(..))")
	public void allMethod() {}
	
	@Before("execution (* iot.tyl.config.GlobalHandler.*(..))")
	public void handlerLog(JoinPoint joinPoint) {
		writeLog(joinPoint);
		
		Object[] objs = joinPoint.getArgs();
		for (Object obj :objs) {
			if (obj instanceof TylBaseException) {
				TylBaseException ex = (TylBaseException) obj;
				
				StackTraceElement[] stackTrace = ex.getStackTrace();
				if (stackTrace.length > 0) {
					StackTraceElement origin = stackTrace[0];
					log.error("origin: {}, method: {}, line: {}", origin.getClassName(), origin.getMethodName(), origin.getLineNumber());
				}
				
				log.error("錯誤原因: {} \n",ex.getLog());
//				if (ex.getCause() != null)
//					log.error("", ex.getCause());
				log.error("原因:",ex);
			}
		}
	}
	
	
	
	
	@Before("allMethod()")
	public void logMethodEntry(JoinPoint joinPoint) throws Throwable {
		writeLog(joinPoint);
	}
	
	public void writeLog(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		String className = signature.getDeclaringType().getSimpleName();
		String methodName = signature.getName();
		Object[] args = joinPoint.getArgs();
		log.info("進入:class:{} ,mehod:{}, param:{}", className, methodName, args);
	}
}
