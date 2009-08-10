package com.j6.framework.jsf.managedbean;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.j6.framework.jsf.application.FacesUtil;

/**
 * Created by IntelliJ IDEA. User: ENG Date: Aug 9, 2007 Time: 4:48:14 PM $id
 */
public class GlobalEnv {
	private static Log log = LogFactory.getLog(GlobalEnv.class);
	private String contextPath;
	private String imagePath;
	private String cssPath;
	private String javaScriptPath;

	public GlobalEnv() {
		contextPath = FacesUtil.getContextPath();
		// log.info("contextPath = " + contextPath);
		imagePath = StringUtils.isBlank(contextPath) ? "/images" : contextPath + "/images";
		cssPath = StringUtils.isBlank(contextPath) ? "/css" : contextPath + "/css";
		javaScriptPath = StringUtils.isBlank(contextPath) ? "/js" : contextPath + "/js";
	}

	public String getJavaScriptPath() {
		return javaScriptPath;
	}

	public String getImagePath() {
		return imagePath;
	}

	public String getCssPath() {
		return cssPath;
	}

	public String getContextPath() {
		return contextPath;
	}
}
