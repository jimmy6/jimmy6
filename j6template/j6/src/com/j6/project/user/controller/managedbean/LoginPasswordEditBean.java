package com.j6.project.user.controller.managedbean;

import com.j6.framework.application.Application;
import com.j6.framework.jsf.application.FacesUtil;
import com.j6.framework.user.manager.UserManager;
import com.j6.framework.user.manager.UserManagerImpl;

public class LoginPasswordEditBean {

	private String newPassword;
	private String retypeNewPassword;
	private String currentPassword;

	private UserManager userManager;
	private LoginBean loginBean;

	public LoginPasswordEditBean() {
		userManager = (UserManager) Application.lookupBean(UserManagerImpl.BEAN_NAME);
		loginBean = (LoginBean) FacesUtil.getManagedBean(LoginBean.BEAN_NAME);
	}

	public String changePassword() {
		userManager.changePassword(loginBean.getUsername(), currentPassword, newPassword, retypeNewPassword);
		return "home";
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getRetypeNewPassword() {
		return retypeNewPassword;
	}

	public void setRetypeNewPassword(String retypeNewPassword) {
		this.retypeNewPassword = retypeNewPassword;
	}

}
