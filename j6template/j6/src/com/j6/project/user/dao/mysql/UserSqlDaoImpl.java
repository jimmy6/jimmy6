package com.j6.project.user.dao.mysql;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.j6.framework.application.Application;
import com.j6.framework.dao.AbstractJdbcDao;
import com.j6.framework.dao.TableModel;
import com.j6.framework.util.J;
import com.j6.project.common.to.UserReport;
import com.j6.project.common.to.UserRoleReport;
import com.j6.project.user.dao.UserSqlDao;

public class UserSqlDaoImpl extends AbstractJdbcDao implements UserSqlDao {

	public UserSqlDaoImpl() {
	}

	public static void main(String a[]) {
		UserSqlDao userDao = (UserSqlDao) Application.lookupBean("userSqlDao");
		TableModel tableModel = new TableModel(5, 1);

		userDao.findAllUserRoles(tableModel);
		J.printNegetif(userDao.findAllUserRoles(tableModel).size() + "..." + tableModel.getResults().size());
	}

	public List<UserRoleReport> findAllUserRoles(TableModel tableModel) {

		return (List<UserRoleReport>) query("select * from app_user_role order by role_name", new UserRoleReport(),
				tableModel);

	}

	public List<UserReport> findAllUserReport(TableModel tableModel, String username, String name) {
		String sql = "select * from app_user WHERE 1=1 ";
		List params = new ArrayList();
		if (StringUtils.isNotBlank(username)) {
			sql = sql + " AND username LIKE ? ";
			params.add("%" + username + "%");
		}
		if (StringUtils.isNotBlank(name)) {
			sql = sql + " AND name LIKE ? ";
			params.add("%" + name + "%");
		}
		sql = sql + " order by username";

		// return (List<UserReport>) query(sql, new UserReport(), tableModel, params.toArray());
		return (List<UserReport>) query(sql, UserReport.class, tableModel, params.toArray());
	}
}
