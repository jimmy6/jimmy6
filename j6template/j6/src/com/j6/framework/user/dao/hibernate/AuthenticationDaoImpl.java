package com.j6.framework.user.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UserDetailsService;

import com.j6.framework.acegi.ExtGrantedAuthority;
import com.j6.framework.user.dao.UserDao;
import com.j6.framework.user.vo.User;
import com.j6.framework.user.vo.UserRole;
import com.j6.framework.util.CollectionUtil;

public class AuthenticationDaoImpl implements UserDetailsService {

	UserDao userDao;

	public AuthenticationDaoImpl() {
	}

	public UserDetails loadUserByUsername(String username) {

		User user = null;

		user = userDao.findUserByUserName(username);

		if (user != null) {
			user.setAuthorities(loadAuthority(user));
		}

		return (UserDetails) user;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * retrieve all the authority from role.activities to user.authority
	 * 
	 * @param user
	 * @return
	 */
	private GrantedAuthority[] loadAuthority(User user) {
		List<ExtGrantedAuthority> retExtGrantedAuthorities = new ArrayList<ExtGrantedAuthority>();
		Set<UserRole> userRoles = user.getUserRoles();

		if (CollectionUtil.isNotEmpty(userRoles))
			for (UserRole userRole : userRoles) {
				// J.printLine(ReflectionUtil.getAllExProperties(userRole," ", ".vo."));
				if (userRole.getStatus())
					for (String act : userRole.getRoleActivities()) {
						// J.printPositif("act = "+act);
						ExtGrantedAuthority extGrantedAuthority = new ExtGrantedAuthority(act);
						retExtGrantedAuthorities.add(extGrantedAuthority);
					}
			}
		return retExtGrantedAuthorities.toArray(new ExtGrantedAuthority[0]);
	}
}
