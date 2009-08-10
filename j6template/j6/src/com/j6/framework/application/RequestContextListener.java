package com.j6.framework.application;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.j6.framework.util.J;

public class RequestContextListener implements ServletRequestListener{

	public RequestContextListener() {
	}

	public void requestDestroyed(ServletRequestEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void requestInitialized(ServletRequestEvent arg0) {
		
		String requestUr =((HttpServletRequest) arg0.getServletRequest()).getRequestURL().toString();
		J.printPositif(requestUr);
		if (requestUr.endsWith(".do")){
//			J.printPositif(((HttpServletRequest) arg0.getServletRequest()).getRequestURL() );
			RequestContextHolder.currentRequestAttributes().setAttribute("spring.userInfo", ((HttpServletRequest) arg0.getServletRequest()).getRequestURL() ,RequestAttributes.SCOPE_REQUEST );
			
//			J.printPositif(RequestContextHolder.currentRequestAttributes().getAttribute("requestUrl", RequestAttributes.SCOPE_REQUEST));
		}
	}

}
