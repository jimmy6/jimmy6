package com.j6.framework.user.dao;

import java.util.List;

import com.j6.framework.dao.BasicDao;
import com.j6.framework.user.vo.User;

public interface UserDao extends BasicDao<User> {

	public User findUserByUserName(String username);

	public List<User> findUserByName(String name);

}
