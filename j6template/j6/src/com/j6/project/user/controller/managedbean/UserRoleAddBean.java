package com.j6.project.user.controller.managedbean;

import com.j6.framework.application.Application;
import com.j6.framework.user.manager.UserManager;
import com.j6.framework.user.vo.UserRole;

public class UserRoleAddBean {
	public static final String BEAN_NAME = "userRoleAddBean";

	private UserManager userManager;
	private UserRole userRole = new UserRole(true);

	public UserRoleAddBean() {
		userManager = (UserManager) Application.lookupBean(UserManager.BEAN_NAME);
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public String saveUserRoleAction() {
		userManager.saveNewUserRole(userRole);
		return UserRoleListBean.BEAN_NAME;
	}

}
