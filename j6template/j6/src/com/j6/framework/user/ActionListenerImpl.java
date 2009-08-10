package com.j6.framework.user;

import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;
import javax.faces.component.ActionSource;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.j6.framework.exception.ValidatorException;
import com.j6.framework.jsf.application.FacesUtil;
import com.j6.framework.util.ReflectionUtil;

/**
 * org.apache.myfaces.application.ActionListenerImpl
 * 
 */
public class ActionListenerImpl implements ActionListener {
	private static final Log log = LogFactory.getLog(ActionListenerImpl.class);
	private String exceptionFormat = "Transaction Error. Please try again or contact administrator.";

	public void processAction(ActionEvent actionEvent) throws AbortProcessingException {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		Application application = facesContext.getApplication();

		ActionSource actionSource = (ActionSource) actionEvent.getComponent();
		MethodBinding methodBinding = actionSource.getAction();

		String fromAction;
		String outcome = null;
		if (methodBinding == null) {
			fromAction = null;
			outcome = null;
		} else {
			fromAction = methodBinding.getExpressionString();

			try {
				outcome = (String) methodBinding.invoke(facesContext, null);
			} catch (EvaluationException e) {
				Throwable cause = e.getCause();
				if (cause != null && cause instanceof ValidatorException) {
					for (String errMsg : ((ValidatorException) cause).getErrorMessages())
						FacesUtil.addErrorMessage(errMsg);

				} else {
					// if manager layer throw runtime exception, it will go in here
					logBackingBeanErr(fromAction, actionEvent, e);
				}
				outcome = "";// display current page
			} catch (Exception e) {
				outcome = "";// display current page
				logBackingBeanErr(fromAction, actionEvent, e);
			}
		}

		if (StringUtils.isEmpty(outcome)) {
			// if outcome is empty, delete the info/error message in session to request scope to avoid duplicate message
			// in ui.
			// not sure the actionListener of component is using this method to call managed bean action. if yes it will
			// skill this solution. problem still existed.
			MessageFilter.sessionToRequestMsg((HttpServletRequest) facesContext.getExternalContext().getRequest());
		}

		NavigationHandler navigationHandler = application.getNavigationHandler();
		navigationHandler.handleNavigation(facesContext, fromAction, outcome);
		// Render Response if needed
		facesContext.renderResponse();
	}

	private void logBackingBeanErr(String fromAction, ActionEvent actionEvent, Exception e) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		Object obj = FacesUtil.getManagedBean(fromAction.substring(2, fromAction.lastIndexOf(".")));

		FacesUtil.addErrorMessage(exceptionFormat + " Caused by: "
				+ (e.getCause() != null ? e.getCause().getMessage() : e.getMessage()) + ".");

		log.error("########## Backing Bean Interceptor ##########\n"
				+ "Error calling action method of component with id "
				+ actionEvent.getComponent().getClientId(facesContext) + "\n"
				+ fromAction.substring(0, fromAction.lastIndexOf(".")) + "} backing bean value = "
				+ ReflectionUtil.getAllExProperties(obj, "   ", ".vo."), e);
		log.error("######## End Backing Bean Interceptor ########");
	}
}
