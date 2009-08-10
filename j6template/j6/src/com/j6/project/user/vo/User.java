package com.j6.project.user.vo;

import org.apache.commons.lang.StringUtils;

import com.j6.framework.acegi.UserDetailsImpl;
import com.j6.framework.user.manager.SecurityManagerImpl;

public class User extends UserDetailsImpl {

	private Integer userId;

	private String username;

	private String name;

	private String password;

	private Boolean enabled;

	private String email;

	private Long deptId;

	public User() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	/**
	 * name which is not for login purpose. will be change to uppercase.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		if (StringUtils.isNotEmpty(name))
			name = name.toUpperCase();
		this.name = name;
	}

	/**
	 * encoded password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * if the arg password's lenght is smaller than 32 then this method will auto encode the password. 32 length is
	 * because current encoded lenght is 32 and the actual password lenght is 20.
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		if (StringUtils.isNotEmpty(password) && password.length() < 32)
			this.password = SecurityManagerImpl.encodeLoginPassword(password);
		else
			this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {

		this.username = username;
	}

	private Boolean getEnabled() {
		return enabled;// can be null to solve findbyexample issue
	}

	public boolean isEnabled() {
		if (getEnabled() == null)
			return false;
		return getEnabled().booleanValue();
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final User other = (User) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

}