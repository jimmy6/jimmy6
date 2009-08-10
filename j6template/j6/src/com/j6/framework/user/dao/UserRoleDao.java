package com.j6.framework.user.dao;

import java.util.List;

import com.j6.framework.dao.BasicDao;
import com.j6.framework.user.vo.UserRole;

public interface UserRoleDao extends BasicDao<UserRole> {

	public List<UserRole> findByUsername(String username);

}
