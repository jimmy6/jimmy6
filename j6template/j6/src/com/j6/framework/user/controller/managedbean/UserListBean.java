package com.j6.framework.user.controller.managedbean;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
 
import com.j6.framework.application.Application;
import com.j6.framework.jsf.application.FacesUtil;
import com.j6.framework.user.manager.UserManager;
import com.j6.framework.user.vo.User;

public class UserListBean {
	public static final String BEAN_NAME = "userListBean";

	private User user = new User(false);
	private UserManager userManager;
//	private DataTable dataTable = new DataTable();

	private List<User> users = new ArrayList<User>();

	public UserListBean() {
		userManager = (UserManager) Application.lookupBean(UserManager.BEAN_NAME);
	}

	public void searchUser() {
		if (StringUtils.isEmpty(user.getUsername()) && StringUtils.isEmpty(user.getName())) {
//			dataTable = new DataTable();
			users = new ArrayList<User>();
		} else {
			users = userManager.findUserByExample(user);
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String editUserAction() {
		UserAndUserRoleEditBean userAndUserRoleEditBean = (UserAndUserRoleEditBean) FacesUtil
				.getManagedBean(UserAndUserRoleEditBean.BEAN_NAME);
//		userAndUserRoleEditBean.setUser((User) dataTable.getRowData());
		FacesUtil.resetManagedBean(BEAN_NAME);
		return UserAndUserRoleEditBean.BEAN_NAME;
	}

	public String resetPassword() {

//		User user = ((User) dataTable.getRowData());
		userManager.resetLoginPassword(user.getUsername());
		FacesUtil.addInfoMessage("The new password of " + user.getName() + " is "
				+ Application.getMessage("usermgmt.resetPassword"));
		return "";
	}

	public List<User> getUsers() {
		return users;
	}
//
//	public DataTable getDataTable() {
//		return dataTable;
//	}
//
//	public void setDataTable(DataTable dataTable) {
//		this.dataTable = dataTable;
//	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}