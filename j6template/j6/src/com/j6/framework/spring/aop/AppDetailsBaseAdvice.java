package com.j6.framework.spring.aop;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;

import javax.faces.context.FacesContext;

import org.springframework.aop.MethodBeforeAdvice;

import com.j6.framework.jsf.application.FacesUtil;
import com.j6.framework.util.CollectionUtil;
import com.j6.framework.vo.VoBase;

public class AppDetailsBaseAdvice implements MethodBeforeAdvice {

	private String loginBeanName = "loginBean";
	private static final int SYSTEM = 0;

	/**
	 * 1)check is AppDetailsBase 2)if not check is collection check is AppDetailsBase
	 * 
	 */
	public void before(Method m, Object[] args, Object target) throws Throwable {

		if (args != null && isCorrectMethod(m))
			for (Object obj : args) {
				// J.printPositif("AppDetailsBaseAdvice = "+obj);
				// 1)
				if (obj instanceof VoBase) {
					fillValueToVo((VoBase) obj, m, args);

					// 2)
				} else if (obj instanceof Collection) {
					Collection collection = (Collection) obj;
					if (CollectionUtil.isNotEmpty(collection)) {
						if (collection.iterator().next() instanceof VoBase) {
							for (Object objOfCollection : collection) {
								fillValueToVo((VoBase) objOfCollection, m, args);
							}
						}
					}
				}
			}

	}

	/**
	 * fill value to appDetailsBase base on method signature.
	 * 
	 * determine whether is save or update save* method is just update createdBy; createdDate;
	 * 
	 * update* method is just update modifiedBy; modifiedDate;
	 * 
	 */
	private void fillValueToVo(VoBase appDetailsBase, Method method, Object[] args) {
		// J.printPositif("fillValueToVo = "+method.getName());
		try {
			int username;
			Object obj = null;

			// TODO :improvement. move this username to before method
			if (FacesContext.getCurrentInstance() != null)
				obj = FacesUtil.getManagedBeanInSession(loginBeanName);

			if (obj != null) {
				username = ((UserInfo) obj).getUserUniqueId();
			} else {
				username = SYSTEM;

				// if (args[0] instanceof UserDetails)
				// username = ((UserDetails)args[0]).getUsername();
				// else
				// username = SYSTEM;
			}

			if (appDetailsBase.getDatetimeAdd() == null) {
				// J.printPositif(".matches(save*) = "+method.getName());
				appDetailsBase.setDatetimeAdd(new Date());
				appDetailsBase.setAddBY(username);
				appDetailsBase.setDatetimeUpdate(new Date());
				appDetailsBase.setUpdateBy(username);
			} else {
				// J.printPositif(".matches(update*) = "+method.getName());

				appDetailsBase.setUpdateBy(username);
				appDetailsBase.setDatetimeUpdate(new Date());
			}
		} catch (Exception e) {
			System.err.println("Error on " + getClass() + "\nCan not find username for AppDetailsBase.");
		}
	}

	/**
	 * check is the method need to fill in info.
	 * 
	 * @param method
	 * @return
	 */
	private boolean isCorrectMethod(Method method) {
		if (method.getName().matches("save.*+") || method.getName().matches("update.*+")) {
			return true;
		} else {
			return false;
		}
	}

	public void setLoginBeanName(String loginBeanName) {
		this.loginBeanName = loginBeanName;
	}

}