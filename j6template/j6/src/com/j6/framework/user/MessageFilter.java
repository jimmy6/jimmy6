package com.j6.framework.user;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Filter to remove messages form the session and put them in the request - to solve the redirect after post issue.
 * 
 * Copy from - org.appfuse.webapp.filter.MessageFilter
 * 
 * @author Matt Raible
 */
public class MessageFilter implements Filter {
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest request = (HttpServletRequest) req;

		sessionToRequestMsg(request);

		chain.doFilter(req, res);
	}

	public static void sessionToRequestMsg(HttpServletRequest request) {
		// grab messages from the session and put them into request
		// this is so they're not lost in a redirect
		Object messages = request.getSession().getAttribute("messages");
		if (messages != null) {
			request.setAttribute("messages", messages);
			request.getSession().removeAttribute("messages");
		}
		// grab errors from the session and put them into request
		// this is so they're not lost in a redirect
		Object errors = request.getSession().getAttribute("errors");

		if (errors != null) {
			request.setAttribute("errors", errors);
			request.getSession().removeAttribute("errors");
		}

	}

	public void init(FilterConfig filterConfig) {
	}

	public void destroy() {
	}
}
