package com.j6.framework.spring.aop;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.j6.framework.exception.ValidatorException;
import com.j6.framework.jsf.application.FacesUtil;
import com.j6.framework.util.J;

public class BackingBeanInterceptor implements MethodInterceptor {

	List<String> exceptionsToUi = new ArrayList<String>();
	private String otherExceptionFormat = "Transaction Error. Please try again or contact administrator.";

	public BackingBeanInterceptor() {
		// default
		exceptionsToUi.add("com.j6.framework.exception.ValidatorException");
	}

	private boolean isNeededToShow(Exception ex) {

		String className = ex.getClass().getName();

		for (String exceptionToUi : exceptionsToUi) {
			if (className.equals(exceptionToUi)) {
				return true;
			}
		}
		return false;
	}

	public void setExceptionsToUi(List<String> exceptionsToUi) {
		this.exceptionsToUi = exceptionsToUi;
	}

	public void setOtherExceptionFormat(String otherExceptionFormat) {
		this.otherExceptionFormat = otherExceptionFormat;
	}

	public Object invoke(MethodInvocation arg) throws Throwable {
		try {
			// J.printLine("1");
			// J.printLine(arg.getThis().toString());
			// J.printLine("2");
			// J.printLine(((LoginBean)arg.getThis()).toString());
			// J.printLine("3");
			// arg.proceed();
			// J.printLine("4");
			// J.printPositif(arg.getThis());
			// J.printLine("5");
		} catch (Exception ex) {
			// J.printLine("6");

			// J.printNegetif(arg.getThis());

			if (FacesContext.getCurrentInstance() != null) {// cater for servlet call so no facecontext.
				if (isNeededToShow(ex)) {
					for (String errMsg : ((ValidatorException) ex).getErrorMessages()) {
						J.printNegetif(errMsg);
						FacesUtil.addErrorMessage(errMsg);
					}
				} else {
					FacesUtil.addErrorMessage(otherExceptionFormat);
				}
				FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(
						FacesContext.getCurrentInstance(), "", "");
				FacesContext.getCurrentInstance().renderResponse();// signal for jsf to skip following phase,direct
				// jump to Render Response phase
			}
		}
		J.printNegetif(">>>>>>>>>>>>>>>>>");

		return arg;
	}

}
