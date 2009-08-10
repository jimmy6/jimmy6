package com.j6.framework.application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.j6.framework.dao.GenericDao;
import com.j6.framework.dao.Sql;
import com.j6.framework.dao.Sqls;
import com.j6.framework.util.ReflectionUtil;

public class Application {
	private static Log log = LogFactory.getLog(Application.class);
	private static String contextPath = "com/j6/project/resources/applicationContext.xml";
	private static Sqls sqls = new Sqls();

	// private static ThreadLocal<HttpServletRequest> httpServletRequestLocal = new ThreadLocal<HttpServletRequest>();

	public static enum MsgResource {
		DATETIME_FORMAT("dateTimeFormat"), DATE_FORMAT("dateFormat");
		private String key;

		private MsgResource(String key) {
			this.key = key;
		}
	}

	protected static void setContextPath(String contextPathNew) {
		contextPath = contextPathNew;
	}

	private static ApplicationContext appContext;

	public Application() {

	}

	protected static synchronized ApplicationContext initialize(ServletContext servletContext) {

		try {
			if (appContext == null) {
				appContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
			}
		} catch (Exception ex) {
			log.warn("WebApplicationContext not found!!!");
			appContext = new ClassPathXmlApplicationContext(contextPath);

		}

		/**
		 * TODO: display error message if bean id name detected. init manager
		 */
		AutoBeanCreatorFactory autoBeanCreatorFactoryManager = (AutoBeanCreatorFactory) appContext
				.getBean("autoBeanCreatorFactoryManager");
		// J.printPositif(autoBeanCreatorFactoryManager);
		List<BeanDefinitionBuilder> listManagers = getBeanDefinitionBuilders(autoBeanCreatorFactoryManager
				.getPackagePatterns().toArray(new String[0]));
		registerSpringBean(listManagers);

		/**
		 * init dao
		 */
		AutoBeanCreatorFactory autoBeanCreatorFactoryDao = (AutoBeanCreatorFactory) appContext
				.getBean("autoBeanCreatorFactoryDao");
		List<BeanDefinitionBuilder> listDaos = getBeanDefinitionBuilders(autoBeanCreatorFactoryDao.getPackagePatterns()
				.toArray(new String[0]));
		registerSpringBean(listDaos);
		// J.printPositif(autoBeanCreatorFactoryDao);
		// /**
		// * init transaction
		// */
		// BeanDefinitionBuilder proxyTransactionBean = BeanDefinitionBuilder
		// .rootBeanDefinition(org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator.class);
		// proxyTransactionBean.addPropertyReference("interceptorNames", "transactionInterceptor");
		// //proxyTransactionBean.addPropertyValue("beanNames", listManagers);
		//
		// String beanNames = "";
		// for (BeanDefinitionBuilder beanDefinitionBuilder : listManagers) {
		// String className = beanDefinitionBuilder.getRawBeanDefinition().getBeanClassName();
		// String simpleName = className.substring(0, className.length() - 4);
		// simpleName = simpleName.substring(simpleName.lastIndexOf(".") + 1);
		// simpleName = simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1);
		// beanNames = beanNames+ ", " +simpleName;
		// }
		// if (beanNames.length()!=0)
		// beanNames = beanNames.substring(2);
		// J.printPositif(beanNames);
		// proxyTransactionBean.addPropertyReference("beanNames", beanNames);
		// registerSpringBean("proxyTransaction", proxyTransactionBean);
		//		

		/**
		 * load all *.sql.xml file
		 */
		PackagePatternInjection packagePatternInjection = (PackagePatternInjection) appContext.getBean("sql");
		List<String> sqlXmls = new ArrayList<String>();

		for (String packagePattern : packagePatternInjection.getPackagePatterns()) {

			try {
				sqlXmls.addAll(ReflectionUtil.findFileNames(packagePattern.substring(0, packagePattern.indexOf("\\")),
						true, packagePattern));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		sqls = xmlToSql(sqlXmls.toArray(new String[0]));

		log.info("Number of Sql = " + sqls.getSqls().size());

		/**
		 * load all *..properties file.
		 */
		// PackagePatternInjection packagePatternInjection = (PackagePatternInjection) appContext.getBean("sql");
		// List<String> sqlXmls = new ArrayList<String>();
		//
		// ResourceBundleMessageSource bundleMessageSource = new ResourceBundleMessageSource();
		// bundleMessageSource.setBasename(basename)
		// TODO see original struts bundle work or not
		return appContext;
	}

	public static Object lookupBean(String beanName) {

		if (appContext == null) {
			if (initialize(null) == null)
				throw new IllegalStateException("SpringContext has not been initialized.");
		}
		return appContext.getBean(beanName);
	}

	// public static Object lookupBean(String beanName, ServletContext
	// servletContext) {
	// return
	// WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).getBean(beanName);
	// }
	//	
	// public static String getMessage(String code,Locale locale)
	// {
	// return getMessage(code,new Object[]{},locale);
	// }
	/**
	 * 
	 * @param xmls
	 *            eg. com.j6.framework.abc.amms.oracle.sql.xml
	 * @return
	 */
	public static Sqls xmlToSql(String... xmls) {
		Sqls sqls = new Sqls();
		Digester digester = new Digester();
		digester.setValidating(false);

		digester.addObjectCreate("sqls", Sqls.class);
		digester.addObjectCreate("sqls/sql", Sql.class);
		digester.addSetProperties("sqls/sql");
		digester.addSetNext("sqls/sql", "addSql");
		digester.addBeanPropertySetter("sqls/sql", "sql");
		digester.addSetProperties("sqls/sql", "id", "id");
		File inputFile;
		for (String xml : xmls) {

			try {
				// change com.j6.framework.abc.amms.oracle.sql.xml to com/j6/framework/abc/amms.oracle.sql.xml
				String newXml = "";
				String[] newXmlsplit = xml.split("\\.");
				int len = newXmlsplit.length;
				newXml = xml.substring(0, (xml.length() - ((newXmlsplit[len - 4] + newXmlsplit[len - 3]
						+ newXmlsplit[len - 2] + newXmlsplit[len - 1]).length() + 3)));
				newXml = newXml.replace(".", "\\") + newXmlsplit[len - 4] + "." + newXmlsplit[len - 3] + "."
						+ newXmlsplit[len - 2] + "." + newXmlsplit[len - 1];
				//
				sqls.addSqls((Sqls) digester.parse(new DefaultResourceLoader().getResource("classpath:" + newXml)
						.getInputStream()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sqls;

	}

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

	/**
	 * "com\\.ilium\\.eapps\\..+?\\.vo\\..+?"
	 * 
	 * @param packagePattern
	 * @return
	 */
	protected static List<BeanDefinitionBuilder> getBeanDefinitionBuilders(String... packagePatterns) {
		Set<String> filePaths = new HashSet<String>();
		List<BeanDefinitionBuilder> beanDefinitionBuilders = new ArrayList<BeanDefinitionBuilder>();
		for (String packagePattern : packagePatterns) {
			try {
				filePaths = ReflectionUtil.findFileNames(packagePattern.substring(0, packagePattern.indexOf("\\")),
						true, packagePattern, ".class");
			} catch (IOException e) {
				e.printStackTrace();
			}
			for (String filePath : filePaths) {
				Class clazz = null;
				try {
					clazz = Thread.currentThread().getContextClassLoader().loadClass(
							filePath.substring(0, filePath.indexOf(".class")));
					if (!clazz.isInterface()) {// TODO just allow class, not
						// interface, how about abstract?
						// check later
						BeanDefinitionBuilder springBean = BeanDefinitionBuilder.rootBeanDefinition(filePath.substring(
								0, filePath.indexOf(".class")));
						springBean.getRawBeanDefinition().setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_NAME);

						beanDefinitionBuilders.add(springBean);
					}
				} catch (ClassNotFoundException e) {
					// it maybe class inside class but after compiled it will generate a file outside
					log.info("Bean will not be register -> " + filePath);
				}

			}
		}
		return beanDefinitionBuilders;
	}

	protected static void registerSpringBean(List<BeanDefinitionBuilder> beanDefinitionBuilders) {
		ConfigurableApplicationContext configApp = (ConfigurableApplicationContext) appContext;
		DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configApp
				.getAutowireCapableBeanFactory();

		for (BeanDefinitionBuilder beanDefinitionBuilder : beanDefinitionBuilders) {
			String className = beanDefinitionBuilder.getRawBeanDefinition().getBeanClassName();
			String simpleName = className.substring(0, className.length() - 4);
			simpleName = simpleName.substring(simpleName.lastIndexOf(".") + 1);
			simpleName = simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1);

			defaultListableBeanFactory.registerBeanDefinition(simpleName, beanDefinitionBuilder.getBeanDefinition());
			log.info("< bean id =\"" + simpleName + "\" class =\"" + className + "\" autowire=\"byName\" />");
		}

	}

	private static void registerSpringBean(String beanName, BeanDefinitionBuilder beanDefinitionBuilder) {
		((DefaultListableBeanFactory) ((ConfigurableApplicationContext) appContext).getAutowireCapableBeanFactory())
				.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
	}

	public static String findSql(String id){
		return sqls.findSql(id).sql;
	}
}
