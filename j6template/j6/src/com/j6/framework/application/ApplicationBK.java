package com.j6.framework.application;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationBK {
	private static final Log log = LogFactory.getLog(ApplicationBK.class);
	private static String contextName = "com/pe/app/inhouse/ctx/applicationContext.xml";

	private static ApplicationContext appContext;

	private ApplicationBK() {
	}

	public static Object lookupBean(String beanName) {
		log.info("Loading startup context...");
		try {
			synchronized (ApplicationBK.class) {
				if (appContext == null)
					appContext = new ClassPathXmlApplicationContext(contextName);
			}
		} catch (Exception e) {
			log.warn("Exception occured initializing startup context.", e);
		}
		return appContext.getBean(beanName);
	}

	// public static String getMessage(String code,Locale locale)
	// {
	// return getMessage(code,new Object[]{},locale);
	// }

	public static String getMessage(String code, Object... param) {
		return getMessage(code, param, Locale.getDefault());
	}

	private static String getMessage(String code, Object[] args, String defaultMsg, Locale loc) {
		return appContext.getMessage(code, args, defaultMsg, loc);
	}

	private static String getMessage(String code, Object[] args, Locale loc) {
		return getMessage(code, args, "?? key " + code + " not found??", loc);
	}
}
