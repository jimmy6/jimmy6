package com.j6.project.user.manager;

import java.util.List;

import com.j6.framework.dao.TableModel;
import com.j6.project.common.to.UserReport;
import com.j6.project.common.to.UserRoleReport;

public interface UserExManager {

	public static final String BEAN_NAME = "userExManager";
	public List<UserRoleReport> findAllUserRoles(TableModel tableModel) ;
	public List<UserReport> findAllUserReport(TableModel tableModel, String username, String name) ;
	
}
