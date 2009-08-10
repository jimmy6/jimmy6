package com.j6.framework.user.vo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.AccessType;
import org.hibernate.validator.Length;
import org.hibernate.validator.Max;
import org.hibernate.validator.NotNull;
import org.springframework.beans.factory.annotation.Required;

import com.j6.framework.vo.EnhancedAppDetailsBase;

@Entity
@Table(name = "APP_USER_ROLE")
@AccessType(value = "field")
@org.hibernate.annotations.Proxy
public class UserRole extends EnhancedAppDetailsBase {
	private static String DELIMITER = ";";

	@Column(name = "status")
	private Boolean status;

	@Id
	@Column(name = "role_name", length = 50)
	private String roleName;

	@Column(name = "role_description", length = 100, nullable = false)
	@Length(max=100)
	@NotNull
	private String roleDescription;

	@ManyToMany(cascade = { CascadeType.REMOVE }, mappedBy = "userRoles", targetEntity = User.class)
	private Set<User> users = new HashSet<User>();
	@Column(name = "role_activity", length = 255, nullable = false)
	private String roleActivity;

	public UserRole() {
	}

	public UserRole(boolean defaultValue) {

	}

	/**
	 * @hibernate.property column = "role_name" length = "50" type = "string" not-null = "true" unique = "true"
	 * 
	 * @return
	 */
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		if (StringUtils.isNotEmpty(roleName))
			;
		roleName = roleName.toUpperCase();
		this.roleName = roleName;
	}

	/**
	 * @hibernate.property type = "boolean" column = "status" not-null = "true"
	 * 
	 */
	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	/**
	 * @hibernate.property column = "role_activity" length = "255" type = "string" not-null = "true"
	 * 
	 * @return
	 */
	private String getRoleActivity() {
		return roleActivity;
	}

	public String[] getRoleActivities() {
		if (StringUtils.isEmpty(roleActivity))
			return new String[0];
		return roleActivity.split(DELIMITER);
	}

	/**
	 * close for hibernate only
	 * 
	 * @param roleActivity
	 */
	private void setRoleActivity(String roleActivity) {
		this.roleActivity = roleActivity;
	}

	public void setRoleActivities(String... roleActivities) {
		// roleActivity = "ROLE_USER";//default role for all user
		// System.out.println(("setRoleActivities = "+roleActivities.toString()));
		roleActivity = "";
		if (roleActivities != null)
			for (String roleAct : roleActivities) {
				roleActivity = roleActivity + DELIMITER + roleAct;
			}
		if (roleActivity.startsWith(";"))
			roleActivity = roleActivity.substring(1);
	}

	/**
	 * @hibernate.property column = "role_description" length = "100" type = "string" not-null = "true"
	 * 
	 * @return
	 */
	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((roleName == null) ? 0 : roleName.hashCode());
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
		final UserRole other = (UserRole) obj;
		if (roleName == null) {
			if (other.roleName != null)
				return false;
		} else if (!roleName.equals(other.roleName))
			return false;
		return true;
	}

}
