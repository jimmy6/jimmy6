package com.j6.project.user.controller.managedbean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.DataModel;

import com.j6.framework.application.Application;
import com.j6.framework.jsf.application.FacesUtil;
import com.j6.framework.user.manager.UserManager;
import com.j6.framework.user.vo.User;

public class UserListBean {
	public static final String BEAN_NAME = "userListBean";

	private User user = new User(false);
	private UserManager userManager;
	private DataModel userDModel = new javax.faces.model.ListDataModel();

	private List<User> users = new ArrayList<User>();

	public UserListBean() { 
		userManager = (UserManager) Application.lookupBean(UserManager.BEAN_NAME);
	}

	public void searchUser() {
//		if (StringUtils.isEmpty(user.getUsername()) && StringUtils.isEmpty(user.getName())) {
//			userDModel = new javax.faces.model.ListDataModel();
//		 
//			users = new ArrayList<User>();
//		}else{
			try {
				
				users = userManager.findUserByExample(user);
				userDModel.setWrappedData(users);
			} catch (Exception e) {
				e.printStackTrace();
			}
//		}
	}

	public DataModel getUserDModel() {
		return userDModel;
	}

	public void setUserDModel(DataModel userDModel) {
		this.userDModel = userDModel;
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
		userAndUserRoleEditBean.setUser((User) userDModel.getRowData());
		FacesUtil.resetManagedBean(BEAN_NAME);
		return UserAndUserRoleEditBean.BEAN_NAME;
	}

	public String resetPassword() {

 		User user = ((User) userDModel.getRowData());
		userManager.resetLoginPassword(user.getUsername());
		FacesUtil.addInfoMessage("The new password of " + user.getName() + " is "
				+ Application.getMessage("usermgmt.resetPassword"));
		return "";
	}

	public List<User> getUsers() {
		return users;
	}
 

	public void setUsers(List<User> users) {
		this.users = users;
	}
 

}