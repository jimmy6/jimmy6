package com.j6.framework.user.manager;

import com.j6.framework.user.vo.User;

public interface SecurityManager {

	public static String BEAN_NAME = "securityManager";

	public User verifyLogin(String username, String password);

}
