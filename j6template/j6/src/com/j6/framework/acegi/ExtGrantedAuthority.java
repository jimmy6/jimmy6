package com.j6.framework.acegi;

import org.acegisecurity.GrantedAuthority;

public class ExtGrantedAuthority implements GrantedAuthority {

	String authority;

	public ExtGrantedAuthority(String authority) {
		setAuthority(authority);
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

}
