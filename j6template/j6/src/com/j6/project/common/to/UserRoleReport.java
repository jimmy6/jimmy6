package com.j6.project.common.to;

import java.sql.ResultSet;
import java.sql.SQLException;
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
import org.springframework.jdbc.core.RowMapper;

import com.j6.framework.vo.EnhancedAppDetailsBase;
 
public class UserRoleReport extends EnhancedAppDetailsBase  implements RowMapper{
	 
	private Boolean status;
	private String roleName;
	private String roleDescription;
	private String roleActivity;

	public UserRoleReport() {
	}

	public UserRoleReport(boolean defaultValue) {

	}
 
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		if (StringUtils.isNotEmpty(roleName))
			roleName = roleName.toUpperCase();
		this.roleName = roleName;
	}
 
	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
 
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
		final UserRoleReport other = (UserRoleReport) obj;
		if (roleName == null) {
			if (other.roleName != null)
				return false;
		} else if (!roleName.equals(other.roleName))
			return false;
		return true;
	}

	public Object mapRow(ResultSet arg0, int arg1) throws SQLException {
		UserRoleReport u = new UserRoleReport();
		u.setRoleName(arg0.getString("role_name")); 
		 
		return u;
	}

}
