package com.j6.project.user.controller.managedbean;

import com.j6.framework.application.Application;
import com.j6.framework.jsf.application.FacesUtil;
import com.j6.framework.user.manager.UserManager;
import com.j6.framework.user.vo.User;

public class UserEditBean {
	public static final String BEAN_NAME = "userEditBean";

	private User user;

	private UserManager userManager;

	public UserEditBean() {
		// it is not a good idea to code anything in constructor because annotation validation will init.
		// this bean before validation phase. it will be trigger on each ajax validation.
		userManager = (UserManager) Application.lookupBean(UserManager.BEAN_NAME);
		LoginBean loginBean = (LoginBean) FacesUtil.getManagedBean(LoginBean.BEAN_NAME);

		User exampleUser = new User(false);
		exampleUser.setUsername(loginBean.getUsername());
		user = userManager.findUserByExample(exampleUser).get(0);
	}

	public String updateUser() {
		userManager.updateUser(user);
		FacesUtil.addInfoMessage("User profile update successfully.");

		// The following output will be change@emaul.com because it is in same session. but it
		// will not commit to the database.
		// user.setEmail("change@emaul.com");
		// LoginBean loginBean = (LoginBean)FacesUtil.getManagedBean(LoginBean.BEAN_NAME);
		// User exampleUser = new User(false);
		// exampleUser.setUsername(loginBean.getUsername());
		// J.printNegetif(userMgmtManager.findUserByExample(exampleUser).get(0).getEmail());

		return "home";
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
