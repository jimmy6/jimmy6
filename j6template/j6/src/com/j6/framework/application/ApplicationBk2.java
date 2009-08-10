package com.j6.framework.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.j6.framework.dao.GenericDao;
import com.j6.framework.user.vo.User;
import com.j6.framework.util.J;
import com.j6.framework.util.ReflectionUtil;

public class ApplicationBk2 {
	private static Log log = LogFactory.getLog(Application.class);

	public static enum MsgResource {
		DATETIME_FORMAT("dateTimeFormat"), DATE_FORMAT("dateFormat");
		private String key;

		private MsgResource(String key) {
			this.key = key;
		}
	}

	private static final String contextPath = "com/privasia/amms/resources/applicationContext.xml";

	private static ApplicationContext appContext;

	private ApplicationBk2() {
		
	}

	protected static synchronized ApplicationContext initialize(final ServletContext servletContext) {
		try {
			if (appContext == null) {
				appContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
			}
		} catch (Exception ex) {
			log.warn("WebApplicationContext not found!!!");
			appContext = new ClassPathXmlApplicationContext(contextPath);
		}
		BeanDefinitionBuilder builder = null;

		builder = BeanDefinitionBuilder
				.rootBeanDefinition("com.j6.framework.user.manager.UserManagerImpl");
		builder.getRawBeanDefinition().setAutowireMode(
				AbstractBeanDefinition.AUTOWIRE_AUTODETECT);
		List<BeanDefinitionBuilder> listManagers = new ArrayList<BeanDefinitionBuilder>();
		listManagers.add(builder);

		BeanDefinitionBuilder proxyBean = BeanDefinitionBuilder
				.rootBeanDefinition("org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator");
		proxyBean.addPropertyReference("interceptorNames",
				"transactionInterceptor");
		proxyBean.addPropertyValue("beanNames", listManagers);

		ConfigurableApplicationContext configApp = (ConfigurableApplicationContext) appContext;
		((DefaultListableBeanFactory) configApp.getBeanFactory())
				.registerBeanDefinition("userManager", builder
						.getBeanDefinition());
		
		
		((DefaultListableBeanFactory) configApp.getBeanFactory())
				.registerBeanDefinition("autoProxy", proxyBean
						.getBeanDefinition());
		configApp.refresh();
		J.printPositif(configApp.getBean("userManager"));	
		
		return appContext;
	}

	public static Object lookupBean(String beanName) {
		if (appContext == null) {
			if (initialize(null) == null)
				throw new IllegalStateException("SpringContext has not been initialized.");
		}
		return appContext.getBean(beanName);
	}

	// public static Object lookupBean(String beanName, ServletContext servletContext) {
	// return WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).getBean(beanName);
	// }
	//	
	// public static String getMessage(String code,Locale locale)
	// {
	// return getMessage(code,new Object[]{},locale);
	// }

	public static String getMessage(String code, Object... param) {
		return getMessage(code, param, Locale.getDefault());
	}

	public static String getMessage(MsgResource msgResource) {
		return getMessage(msgResource.key);
	}

	private static String getMessage(String code, Object[] args, String defaultMsg, Locale loc) {
		return appContext.getMessage(code, args, defaultMsg, loc);
	}

	private static String getMessage(String code, Object[] args, Locale loc) {
		return getMessage(code, args, "?? key " + code + " not found??", loc);
	}
}
