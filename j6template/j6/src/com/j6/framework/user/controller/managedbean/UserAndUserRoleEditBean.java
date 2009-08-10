package com.j6.framework.user.controller.managedbean;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import com.j6.framework.application.Application;
import com.j6.framework.user.manager.UserManager;
import com.j6.framework.user.vo.User;
import com.j6.framework.user.vo.UserRole;
import com.j6.framework.util.CollectionUtil;
import com.j6.framework.util.J;
import com.j6.framework.util.VoUtil;

public class UserAndUserRoleEditBean {
	public static final String BEAN_NAME = "userAndUserRoleEditBean";

	private User user = new User(false);
	private List<UserRole> userRoles;
	private DataModel userRoleDModel = new ListDataModel();
	private UserManager userManager;

	public UserAndUserRoleEditBean() {
		userManager = (UserManager) Application.lookupBean(UserManager.BEAN_NAME);
	}

	public String updateUser() {
		J.printPositif(user.getDob());
		userManager.updateUser(user, (List) userRoleDModel.getWrappedData());
		return UserListBean.BEAN_NAME;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	// private void checkSelectedRole(){
	// for (){
	//			
	// }
	// }

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public DataModel getUserRoleDModel() {

		// if statement inside the gettter to avoid call multiple time.
		if (CollectionUtil.isEmpty(userRoles)) {
			userRoles = userManager.findAllUserRoles();
			List<UserRole> list = userManager.findUserRoleByUsername(user.getUsername());

			VoUtil.check(list, true);

			// the purpose is just to erase the duplicate object so use Set.
			Set<UserRole> set = new HashSet<UserRole>(userRoles);
			set.addAll(list);
			userRoles.clear();
			userRoles.addAll(set);
			// /////////////////////////////////////////////////////////////
			userRoleDModel.setWrappedData(userRoles);
		}
		return userRoleDModel;
	}

	public void setUserRoleDModel(DataModel userRoleDModel) {
		this.userRoleDModel = userRoleDModel;
	}

}