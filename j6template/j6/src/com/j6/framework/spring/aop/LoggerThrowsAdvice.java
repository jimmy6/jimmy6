package com.j6.framework.spring.aop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.ThrowsAdvice;

import com.j6.framework.util.ReflectionUtil;

/**
 * <pre>
 * Log error when throw.
 * Output:
 *    [ERROR] 27-02-06 00:53:23 : Class = com.foremobile.gateway.usermgmt.manager.UserMgmtManagerImpl@fc40ae (http-8080-Processor25)
 *    [ERROR] 27-02-06 00:53:23 : Method = public abstract void com.foremobile.gateway.usermgmt.manager.UserMgmtManager.saveMe(com.foremobile.gateway.usermgmt.vo.User,java.lang.String) (http-8080-Processor25)
 *    [ERROR] 27-02-06 00:53:23 : Arg = com.foremobile.gateway.usermgmt.vo.User@fa706d
 *    accountNonExpired=false;accountNonLocked=false;appId=null;authorities=null;class=class com.foremobile.gateway.usermgmt.vo.User;createdBy=null;createdDate=Mon Feb 27 00:53:23 SGT 2006;credentialsNonExpired=false;email=emaill;enabled=false;modifiedBy=null;modifiedDate=null;name=namee;password=null;userRoles=[];username=null; (http-8080-Processor25)
 *    [ERROR] 27-02-06 00:53:23 : Arg = 1234
 *    bytes=[B@98cbf7;class=class java.lang.String; (http-8080-Processor25)
 *    [ERROR] 27-02-06 00:53:23 : java.lang.RuntimeException:  (http-8080-Processor25)
 *    [ERROR] 27-02-06 00:53:23 : Exception while invoking expression #{loginBean.loginAction} (http-8080-Processor25)
 *    java.lang.RuntimeException:
 *    at com.foremobile.gateway.usermgmt.manager.UserMgmtManagerImpl.callagain(UserMgmtManagerImpl.java:37)
 *    at com.foremobile.gateway.usermgmt.manager.UserMgmtManagerImpl.saveMe(UserMgmtManagerImpl.java:32)
 *    at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
 *    at sun.reflect.NativeMethodAccessorImpl.invoke(Unknown Source)
 *    at sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)
 *   .............
 *    
 * If the arg = [com.test.vo.User@275, com.test.vo.User@275, com.test.vo.User@275]
 * which mean collection. Follwing is example.
 *    [ERROR] 27-02-06 00:53:23 : Class = com.foremobile.gateway.usermgmt.manager.UserMgmtManagerImpl@fc40ae (http-8080-Processor25)
 *    [ERROR] 27-02-06 00:53:23 : Method = public abstract void com.foremobile.gateway.usermgmt.manager.UserMgmtManager.saveMe(com.foremobile.gateway.usermgmt.vo.User,java.lang.String) (http-8080-Processor25)
 *    [ERROR] 27-02-06 00:53:23 : Arg = [com.test.vo.User@275, com.test.vo.User@275, com.test.vo.User@275]
 *    ............
 * </pre>
 */
public class LoggerThrowsAdvice implements ThrowsAdvice {
	private static final Log log = LogFactory.getLog(LoggerThrowsAdvice.class);

	private String voPath = ".vo.";// default
	// private AopExecutor aopExcecutor;
	private List<String> exceptionsNotToLog = new ArrayList<String>();

	public LoggerThrowsAdvice() {
	}

	// public void afterThrowing(Exception ex) throws Throwable {
	//
	// }

	public void afterThrowing(Method m, Object[] args, Object target, Exception exception) {
		try {
			if (isNeedToLog(exception)) {

				log.error("########## Logger Throws Advice ##########\nClass = " + target + "\nMethod = " + m);
				if (args != null)
					for (Object arg : args) {
						log.error("Arg = " + arg + ReflectionUtil.getAllExProperties(arg, "   ", voPath));
					}
				log.error(exception, exception);
				log.error("######## End Logger Throws Advice ########");
			}
		} catch (Exception e) {

			log.error(exception, exception);
			log.error("LoggerThrowsAdvice Error : " + e.getMessage() + "######## End Logger Throws Advice ########");

		}
	}

	private boolean isNeedToLog(Exception ex) {

		String className = ex.getClass().getName();

		for (String exceptionNotToLog : exceptionsNotToLog) {
			if (className.equals(exceptionNotToLog)) {
				return false;
			}
		}
		return true;
	}

	public void setVoPath(String voPath) {
		this.voPath = voPath;
	}

	//	
	// public void setAopExcecutor(AopExecutor aopExcecutor) {
	// this.aopExcecutor = aopExcecutor;
	// }
	//	
	public void setExceptionsNotToLog(List<String> exceptionsNotToLog) {
		this.exceptionsNotToLog = exceptionsNotToLog;
	}
}
