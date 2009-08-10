package com.j6.framework.user.manager;

import java.util.List;
import java.util.Set;

import com.j6.framework.user.vo.User;
import com.j6.framework.user.vo.UserRole;

public interface UserManager {

	public static final String BEAN_NAME = "userManager";

	/**
	 * User
	 * 
	 * @throws Exception
	 */
	public void updateUser(User user);

	public void updateUser(User user, List<UserRole> userRole);

	public void saveNewUser(User user, List<UserRole> userRoles);

	public List<User> findUserByExample(User userExample);

	public void resetLoginPassword(String userName);

	public void changePassword(String username, String currentPassword, String newPassword, String retypeNewPassword);

	/** UserRole * */
	public void saveNewUserRole(UserRole userRole);

	public void updateUserRole(UserRole userRole);

	public UserRole getUserRole(String roleName);

	public List<UserRole> findAllUserRoles();

	public List<UserRole> findUserRoleByUsername(String username);

	public List<UserRole> findUserRoleByExample(UserRole exampleUserRole);

	public void deleteUserRoles(Set<UserRole> userRoles);

}
