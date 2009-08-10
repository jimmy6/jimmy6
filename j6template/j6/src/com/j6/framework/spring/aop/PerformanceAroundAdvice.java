package com.j6.framework.spring.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PerformanceAroundAdvice implements MethodInterceptor {
	private static final Log log = LogFactory.getLog(LoggerThrowsAdvice.class);

	public PerformanceAroundAdvice() {
		log.debug("Creating..PerformanceAroundAdvice()");
	}

	public Object invoke(MethodInvocation arg0) throws Throwable {
		long start = System.nanoTime();
		Object obj = arg0.proceed();
		long end = System.nanoTime();
		long execution = end - start;
		log.info("Performance. Method = " + arg0.getMethod() + "\n  .Taken Time = " + execution + "(ns)  " + execution
				/ 100000 + "(ms)");
		return obj;
	}

}