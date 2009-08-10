package com.j6.framework.jsf.managedbean;

import com.j6.framework.spring.aop.UserInfo;

public interface LoginBackingBean extends UserInfo {

	public abstract String login();

	public abstract void setUsername(String username);

	public abstract void setPassword(String password);
}
