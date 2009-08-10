package com.j6.framework.application;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

public class JContextLoaderListener extends ContextLoaderListener  {
	public JContextLoaderListener() {
	}

	public void contextInitialized(final ServletContextEvent servletContextEvent) {
		super.contextInitialized(servletContextEvent);
		Application.initialize(servletContextEvent.getServletContext());
	}
	
}
