package com.j6.project.user.dao;

import java.util.List;

import com.j6.framework.dao.TableModel;
import com.j6.project.common.to.UserReport;
import com.j6.project.common.to.UserRoleReport;

public interface UserSqlDao {

	public List<UserRoleReport> findAllUserRoles(TableModel tableModel);
	public List<UserReport> findAllUserReport(TableModel tableModel, String username, String name);

}
