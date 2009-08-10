package com.j6.project.user.manager;

import java.util.List;

import com.j6.framework.dao.TableModel;
import com.j6.framework.user.dao.UserDao;
import com.j6.framework.user.dao.UserRoleDao;
import com.j6.project.common.to.UserReport;
import com.j6.project.common.to.UserRoleReport;
import com.j6.project.user.dao.UserSqlDao;

public class UserExManagerImpl implements UserExManager {
	private UserDao userDao;
	private UserRoleDao userRoleDao;
	private UserSqlDao userSqlDao;

	public UserExManagerImpl() {
	}

	public List<UserRoleReport> findAllUserRoles(TableModel tableModel) {
		return userSqlDao.findAllUserRoles(tableModel);
	}

	public List<UserReport> findAllUserReport(TableModel tableModel, String username, String name) {
		return userSqlDao.findAllUserReport(tableModel, username, name);
	}

	public void setUserSqlDao(UserSqlDao userSqlDao) {
		this.userSqlDao = userSqlDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setUserRoleDao(UserRoleDao userRoleDao) {
		this.userRoleDao = userRoleDao;
	}

}
