package com.j6.framework.user.vo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.AccessType;

import com.j6.framework.acegi.UserDetailsImpl;
import com.j6.framework.user.manager.SecurityManagerImpl;

@Entity(name = "APP_USER")
@Table(name = "APP_USER")
@AccessType(value = "field")
public class User extends UserDetailsImpl {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private Integer userId;

	@Column(length = 50, name = "username")
	private String username;

	@Column(nullable = false, length = 50, name = "name")
	private String name;

	@Column(nullable = false, length = 32, name = "password")
	private String password;

	/**
	 * hibernate will use the global variable to get value. if this set to small b(boolean). find by example not work.
	 * use Boolean object instead of primitive datatype is because the findbyexample issue. primitive datatype by
	 * default already have the value there so will affect the example object.
	 */
	@Column(nullable = false, name = "enabled")
	private Boolean enabled;

	@Column(name = "email")
	@org.hibernate.validator.Email
	@org.hibernate.validator.NotNull
	@org.hibernate.validator.Length(min = 4, max = 50)
	private String email;// if hibernate.validator and javax.persistence is using at the same time, hibernate will be
	// used.

	@Column(name = "dob")
	@org.hibernate.validator.NotNull
	@org.hibernate.validator.Pattern(regex = "(\\d{2})-(\\d{2})-(\\d{4})")
	private Date dob;

	@org.hibernate.validator.NotNull
	@ManyToMany(targetEntity = UserRole.class)
	@JoinTable(name = "APP_USER_IN_ROLE", joinColumns = { @JoinColumn(name = "fk_user_id") }, inverseJoinColumns = { @JoinColumn(name = "fk_user_role") })
	private Set<UserRole> userRoles = new HashSet<UserRole>();

	/**
	 * close for hibernate use.
	 * 
	 * 
	 */
	public User() {
	}

	/**
	 * 
	 * @param defaultValue
	 *            true = init default value. set to false if use findbyexample.
	 */
	public User(boolean defaultValue) {
		if (defaultValue)
			enabled = true;
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

	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
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

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Long getDeptId() {
		return 1L;
	}

}