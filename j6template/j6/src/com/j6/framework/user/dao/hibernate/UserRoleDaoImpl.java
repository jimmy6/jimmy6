package com.j6.framework.user.dao.hibernate;

import java.util.List;

import com.j6.framework.dao.AbstractDao;
import com.j6.framework.user.dao.UserRoleDao;
import com.j6.framework.user.vo.UserRole;

public class UserRoleDaoImpl extends AbstractDao<UserRole> implements UserRoleDao {

	private static final String QUERY_BY_USERNAME = "select userRole from UserRole userRole join userRole.users users where users.username=?";

	public UserRoleDaoImpl() {
		super(new UserRole(false));
	}

	public List<UserRole> findByUsername(String username) {
		return super.findQueryAsList(QUERY_BY_USERNAME, username);
	}

}