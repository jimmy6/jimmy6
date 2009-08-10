package com.j6.framework.user.dao.hibernate;

import java.util.List;

import com.j6.framework.dao.AbstractDao;
import com.j6.framework.user.dao.UserDao;
import com.j6.framework.user.vo.User;

public class UserDaoImpl extends AbstractDao<User> implements UserDao {

	private static final String QUERY_BY_USERNAME = "FROM u IN " + User.class + " WHERE u.username=?";
	private static final String QUERY_BY_NAME = "FROM u IN " + User.class + " WHERE u.name=?";

	public UserDaoImpl() {
		super(new User(false));
	}

	public User findUserByUserName(String username) {
		return super.findUnique(QUERY_BY_USERNAME, username);
	}

	public List<User> findUserByName(String name) {
		return super.findQueryAsList(QUERY_BY_NAME, name);
	}

}
