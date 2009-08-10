package com.j6.project.user.controller.managedbean;

import java.util.List;

import com.j6.framework.application.Application;
import com.j6.framework.user.manager.UserManager;
import com.j6.framework.user.vo.User;
import com.j6.framework.user.vo.UserRole;

public class UserAddBean {
	public static final String BEAN_NAME = "userEditBean";

	private User userToSave = new User(true);
	private List<UserRole> userRoles;

	private UserManager userManager;

	public UserAddBean() {
		userManager = (UserManager) Application.lookupBean(UserManager.BEAN_NAME);
		userRoles = userManager.findAllUserRoles();
	}

	public String saveUser() {
		userManager.saveNewUser(userToSave, userRoles);
		return UserListBean.BEAN_NAME;
	}

	public User getUserToSave() {
		return userToSave;
	}

	public void setUserToSave(User userToSave) {
		this.userToSave = userToSave;
	}

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

}
