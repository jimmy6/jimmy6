package com.j6.project.user.controller.managedbean;

import com.j6.framework.application.Application;
import com.j6.framework.user.manager.UserManager;
import com.j6.framework.user.vo.UserRole;
import com.j6.framework.util.J;

public class UserRoleEditBean {
	public static final String BEAN_NAME = "userRoleEditBean";

	private UserManager userManager;
	private UserRole userRole = new UserRole(false);
 
	public UserRoleEditBean() {
		userManager = (UserManager) Application.lookupBean(UserManager.BEAN_NAME);
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public String updateUserRoleAction() {

		UserRole dbUserRole = userManager.getUserRole(userRole.getRoleName());

		dbUserRole.setRoleActivities(userRole.getRoleActivities());
		dbUserRole.setRoleDescription(userRole.getRoleDescription());
		dbUserRole.setStatus(userRole.getStatus());

		userManager.updateUserRole(dbUserRole);

		return UserRoleListBean.BEAN_NAME;
	}
}