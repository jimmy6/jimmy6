package com.j6.framework.jsf.application;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.el.ValueBinding;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.acegisecurity.context.SecurityContextHolder;
import org.ajax4jsf.component.html.HtmlAjaxCommandButton;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.shared_tomahawk.component.DisplayValueOnlyCapable;
import org.richfaces.component.html.HtmlSimpleTogglePanel;

import com.j6.framework.jsf.managedbean.LoginBackingBean;
import com.j6.framework.user.controller.managedbean.LoginBean;
import com.j6.framework.user.vo.User;

public class FacesUtil {
	private static Log log = LogFactory.getLog(FacesUtil.class);

	public static String getMessageResourceString(FacesContext ctx, String key, Object params[]) {

		String text = null;

		Locale locale = ctx.getViewRoot().getLocale();
		ResourceBundle bundle = ResourceBundle.getBundle(ctx.getApplication().getMessageBundle(), locale);

		try {
			text = bundle.getString(key);
		} catch (MissingResourceException e) {
			text = "?? key " + key + " not found ??";
		}

		if (params != null) {
			MessageFormat mf = new MessageFormat(text, locale);
			text = mf.format(params, new StringBuffer(), null).toString();
		}

		return text;
	}

	public static ServletContext getServletContext() {
		return (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
	}

	/**
	 * get managed bean. If not exist then create new Managed Bean
	 * 
	 * @param beanName
	 * @return
	 */
	public static Object getManagedBean(String beanName) {
		Object o = null;
		if (beanName.equalsIgnoreCase(LoginBean.BEAN_NAME)) {
			if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(beanName) != null) {
				o = getValueBinding(getJsfEl(beanName)).getValue(FacesContext.getCurrentInstance());
			} else {
				String password = FacesContext.getCurrentInstance().getExternalContext().getInitParameter(
						"com.j6.loginPassword");
				String username = FacesContext.getCurrentInstance().getExternalContext().getInitParameter(
						"com.j6.loginUsername");
				LoginBackingBean login = (LoginBackingBean) getValueBinding(getJsfEl(beanName)).getValue(
						FacesContext.getCurrentInstance());
				login.setUsername(username);
				login.setPassword(password);

				try {
					login.login();
					o = login;
				} catch (Exception e) {
					log.error("com.privasia.loginPasswor/username setting wrong in web.xml. Maybe password wrong ");
				}

			}
		} else {
			o = getValueBinding(getJsfEl(beanName)).getValue(FacesContext.getCurrentInstance());
		}
		return o;
	}

	public static void resetManagedBean(String beanName) {
		getValueBinding(getJsfEl(beanName)).setValue(FacesContext.getCurrentInstance(), null);
	}

	public static void setManagedBeanInSession(String beanName, Object managedBean) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(beanName, managedBean);
	}

	public static Object getManagedBeanInSession(String beanName) {
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(beanName);
	}

	public static void setManagedBeanInRequest(String beanName, Object managedBean) {
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put(beanName, managedBean);
	}

	public static Object getManagedBeanInRequest(String beanName) {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get(beanName);
	}

	public static String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name);
	}

	public static Map getRequestParameterMap() {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
	}

	public static Map getRequestMap() {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
	}

	public static Map getRequestHeaderMap() {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestHeaderMap();
	}

	public static Map getSessionMap() {
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
	}

	public static Map getApplicationMap() {
		return FacesContext.getCurrentInstance().getExternalContext().getApplicationMap();
	}

	public static void dispatch(String path) throws IOException {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.getExternalContext().dispatch(path);
	}

	public static void redirect(String url) throws IOException {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.getExternalContext().redirect(url);
	}

	public static void addInfoMessage(String msg) {
		List<String> msgs = (List<String>) getSessionMap().get("messages");

		if (msgs == null) {
			msgs = new ArrayList<String>();
		}
		msgs.add(msg);
		getSessionMap().put("messages", msgs);
	}

	// public static void addInfoMessage(String clientId, String msg) {
	// FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(FacesMessage.SEVERITY_INFO, "", msg));
	// }

	public static void addErrorMessage(String msg) {
		List<String> errors = (List<String>) getSessionMap().get("errors");

		if (errors == null) {
			errors = new ArrayList<String>();
		}
		errors.add(msg);
		getSessionMap().put("errors", errors);
	}

	/**
	 * Just work for current page.
	 * 
	 * @param clientId
	 * @param msg
	 */
	public static void addErrorMessage(String clientId, String msg) {
		FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", msg));
	}

	public static Integer evalInt(String el) {
		if (el == null) {
			return null;
		}

		if (UIComponentTag.isValueReference(el)) {
			Object value = getElValue(el);

			if (value == null) {
				return null;
			} else if (value instanceof Integer) {
				return (Integer) value;
			} else {
				return new Integer(value.toString());
			}
		} else {
			return new Integer(el);
		}
	}

	private static Application getApplication() {
		ApplicationFactory appFactory = (ApplicationFactory) FactoryFinder
				.getFactory(FactoryFinder.APPLICATION_FACTORY);
		return appFactory.getApplication();
	}

	private static ValueBinding getValueBinding(String el) {
		return getApplication().createValueBinding(el);
	}

	private static HttpServletRequest getServletRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	private static Object getElValue(String el) {
		return getValueBinding(el).getValue(FacesContext.getCurrentInstance());
	}

	private static String getJsfEl(String value) {
		return "#{" + value + "}";
	}

	public static void logout() {
		// SecurityContextHolder.getContext().setAuthentication(null);
		SecurityContextHolder.clearContext();
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.invalidate();
	}

	public static String getContextPath() {
		return getServletRequest().getContextPath();
	}

	/**
	 * Locate an UIComponent in view root with its component id. Use a recursive way to achieve this. Taken from
	 * http://www.jroller.com/page/mert?entry=how_to_find_a_uicomponent
	 * 
	 * @param id
	 *            UIComponent id
	 * @return UIComponent object
	 */
	public static UIComponent findComponentInRoot(String id) {
		UIComponent component = null;
		FacesContext facesContext = FacesContext.getCurrentInstance();
		if (facesContext != null) {
			UIComponent root = facesContext.getViewRoot();
			component = findComponent(root, id);
		}
		return component;
	}

	/**
	 * Locate an UIComponent from its root component. Taken from
	 * http://www.jroller.com/page/mert?entry=how_to_find_a_uicomponent
	 * 
	 * @param base
	 *            root Component (parent)
	 * @param id
	 *            UIComponent id
	 * @return UIComponent object
	 */
	public static UIComponent findComponent(UIComponent base, String id) {
		if (id.equals(base.getId()))
			return base;

		UIComponent children = null;
		UIComponent result = null;
		Iterator childrens = base.getFacetsAndChildren();
		while (childrens.hasNext() && (result == null)) {
			children = (UIComponent) childrens.next();
			if (id.equals(children.getId())) {
				result = children;
				break;
			}
			result = findComponent(children, id);
			if (result != null) {
				break;
			}
		}
		return result;
	}

	public static void hideCommandButton(UIComponent base) {
		if (base == null)
			return;

		UIComponent children = null;

		Iterator childrens = base.getFacetsAndChildren();
		while (childrens.hasNext()) {
			children = (UIComponent) childrens.next();

			if (children instanceof HtmlAjaxCommandButton || children instanceof HtmlCommandButton
					|| children instanceof org.apache.myfaces.component.html.ext.HtmlCommandButton) {

				children.setRendered(false);
			}
			hideCommandButton(children);
		}

	}

	/**
	 * http://wiki.apache.org/myfaces/ClearInputComponents
	 */
	public static void resetValues(UIComponent base) {
		if (base == null)
			return;

		UIComponent children = null;

		Iterator childrens = base.getFacetsAndChildren();
		while (childrens.hasNext()) {
			children = (UIComponent) childrens.next();

			if (children instanceof EditableValueHolder) {
				EditableValueHolder editableValueHolder = (EditableValueHolder) children;
				editableValueHolder.setSubmittedValue(null);
				// The following is only needed for immediate input components
				// but it won't do any harm in other situations..
				editableValueHolder.setValue(null);
				editableValueHolder.setLocalValueSet(false);
			}
			resetValues(children);
		}
	}

	public static User getUser() {
		return ((LoginBean) getManagedBean(LoginBean.BEAN_NAME)).getUser();
	}

	public static void viewMode(UIComponent base) {
		if (base == null)
			return;

		UIComponent children = null;

		Iterator childrens = base.getFacetsAndChildren();
		int i = 0;
		while (childrens.hasNext()) {
			i++;
			children = (UIComponent) childrens.next();

			if (children instanceof DisplayValueOnlyCapable) {
				((DisplayValueOnlyCapable) children).setDisplayValueOnly(true);
				((DisplayValueOnlyCapable) children).setDisplayValueOnlyStyle("");
				((DisplayValueOnlyCapable) children).setDisplayValueOnlyStyleClass("");
			} else if (children instanceof HtmlSimpleTogglePanel) {
				((HtmlSimpleTogglePanel) children).setRendered(false);
				HtmlOutputText htmlOutputText = new HtmlOutputText();
				htmlOutputText.setValue(((HtmlSimpleTogglePanel) children).getLabel());
				htmlOutputText.setStyleClass("dr-toolbar-ext pagetitle");
				htmlOutputText.setTransient(true);

				base.getChildren().add(i, htmlOutputText);
			}

			viewMode(children);
		}

	}

	public static void resetFacesContext() {

		FacesContextFactory contextFactory = (FacesContextFactory) FactoryFinder
				.getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
		LifecycleFactory lifecycleFactory = (LifecycleFactory) FactoryFinder
				.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
		Lifecycle lifecycle = lifecycleFactory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);

		FacesContext oldContext = FacesContext.getCurrentInstance();
		ServletContext servletContext = (ServletContext) oldContext.getExternalContext().getContext();
		HttpServletRequest servletRequest = (HttpServletRequest) oldContext.getExternalContext().getRequest();
		HttpServletResponse servletResponse = (HttpServletResponse) oldContext.getExternalContext().getResponse();

		FacesContext newContext = contextFactory.getFacesContext(servletContext, servletRequest, servletResponse,
				lifecycle);

		newContext.setViewRoot(FacesContext.getCurrentInstance().getViewRoot());
		MyInnerClass.setFacesContext(newContext);

	}

	// You need an inner class to be able to call FacesContext.setCurrentInstance
	// since it's a protected method
	private abstract static class MyInnerClass extends FacesContext {
		protected static void setFacesContext(FacesContext facesContext) {
			FacesContext.setCurrentInstance(facesContext);
		}
	}
}
