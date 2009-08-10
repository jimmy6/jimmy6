package com.j6.framework.user.controller.managedbean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
 
import com.j6.framework.application.Application;
import com.j6.framework.jsf.application.FacesUtil;
import com.j6.framework.user.manager.UserManager;
import com.j6.framework.user.vo.UserRole;
import com.j6.framework.util.CollectionUtil;

public class UserRoleListBean {
	public static final String BEAN_NAME = "userRoleListBean";

	private UserManager userManager;

//	private DataTable userRoleDModel = new DataTable();
	private List<UserRole> userRoleSelected = new ArrayList<UserRole>();

	private List<UserRole> userRoles = new ArrayList<UserRole>();

	public UserRoleListBean() {
		userManager = (UserManager) Application.lookupBean(UserManager.BEAN_NAME);
		userRoles = userManager.findAllUserRoles();
	}

	// public String deleteUserRoles(){
	//		
	// }

//	public DataTable getUserRoleDModel() {
//		// userRoleDModel.setWrappedData(userRoles);
//		return userRoleDModel;
//	}
//
//	public String editUserRoleAction() {
//		UserRoleEditBean userRoleModificationBean = (UserRoleEditBean) FacesUtil
//				.getManagedBean(UserRoleEditBean.BEAN_NAME);
//		userRoleModificationBean.setUserRole((UserRole) userRoleDModel.getRowData());
//		return UserRoleEditBean.BEAN_NAME;
//	}
//
//	public void setUserRoleDModel(DataTable userRoleDModel) {
//		this.userRoleDModel = userRoleDModel;
//	}

	public String deleteUserRolesAction() {

		if (CollectionUtil.isNotEmpty(userRoles)) {
			userManager.deleteUserRoles(new HashSet<UserRole>(userRoleSelected));
		} else {
			FacesUtil.addInfoMessage("No user role to be deleted.");
		}
		return BEAN_NAME;

	}

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public List<UserRole> getUserRoleSelected() {
		return userRoleSelected;
	}

	public void setUserRoleSelected(List<UserRole> userRoleSelected) {
		this.userRoleSelected = userRoleSelected;
	}

}
