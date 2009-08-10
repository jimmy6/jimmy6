package com.j6.framework.user.manager;

import java.util.List;
import java.util.Set;

import com.j6.framework.application.Application;
import com.j6.framework.dao.TableModel;
import com.j6.framework.exception.ValidatorException;
import com.j6.framework.user.dao.UserDao;
import com.j6.framework.user.dao.UserRoleDao;
import com.j6.framework.user.vo.User;
import com.j6.framework.user.vo.UserRole;
import com.j6.framework.util.CollectionUtil;
import com.j6.framework.util.VoUtil;

public class UserManagerImpl implements UserManager {
	private UserDao userDao;
	private UserRoleDao userRoleDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setUserRoleDao(UserRoleDao userRoleDao) {
		this.userRoleDao = userRoleDao;
	}

	public UserManagerImpl() {
	}

	/**
	 * for acegi call only.
	 */
	// public UserDetails loadUserByUsername(String username) {
	//		
	// UserDetails user = null;
	// try{
	// user = userDao.findUserByUserName(username);
	//
	// }catch (Exception e){
	// e.printStackTrace();
	// }
	//	
	// return (UserDetails) user;
	// }
	/**
	 * Validation: 1. ERR0002 - check username which can not be duplicated.
	 * 
	 * @param user =
	 *            user to be save.
	 * @param userRoles =
	 * only assign to user if properties of uiSelected is true. @
	 * @throws ValidatorException
	 * 
	 */
	public void saveNewUser(User user, List<UserRole> userRoles) {

		User queryUser = userDao.findUserByUserName(user.getUsername());
		// 1
		if (queryUser == null) {
			user.setUserRoles(VoUtil.filterSelected(userRoles));
			userDao.save(user);
		} else {
			throw new ValidatorException("User role name already in use.");

		}

	}

	public List<UserRole> findAllUserRoles() {
		return userRoleDao.loadAll();
	}
 
	public void updateUser(User user) {
		userDao.update(user);
	}

	/**
	 * 
	 * @param userRoles =
	 * only assign to user if properties of selected is true. @
	 */
	public void updateUser(User user, List<UserRole> userRoles) {

		user.setUserRoles(VoUtil.filterSelected(userRoles));
		userDao.update(user);
	}

	public void updateUserRole(UserRole userRole) {
		userRoleDao.update(userRole);
	}

	/**
	 * validation. this checking done automatically in ui 1)ERR0004 - check whether roleName is already in use. @
	 * @throws ValidatorException
	 */
	public void saveNewUserRole(UserRole userRole) {

		// 1)
		// UserRole userRoleExample = new UserRole(false);
		// userRoleExample.setRoleName(userRole.getRoleName());
		// List<UserRole> userRoles = userRoleDao.findByExample(userRoleExample);
		// if (CollectionUtil.isNotEmpty(userRoles)){
		// throw new ValidatorException("User role name already in use.");
		// }
		//

		userRoleDao.save(userRole);
	}

	public UserRole getUserRole(String roleName) {
		return userRoleDao.get(roleName);
	}

	public List<UserRole> findUserRoleByUsername(String username) {
		return userRoleDao.findByUsername(username);
	}

	public List<User> findUserByExample(User userExample) {
		return userDao.findByExample(userExample);
	}

	public List<UserRole> findUserRoleByExample(UserRole userRoleExample) {
		return userRoleDao.findByExample(userRoleExample);
	}

	/**
	 * Validation : 1) ERR0003 - Check whether user object's username is existed.
	 * 
	 * @param userName @
	 * @throws ValidatorException
	 */
	public void resetLoginPassword(String userName) {

		User exampleUser = new User(false);
		exampleUser.setUsername(userName);
		List<User> users = userDao.findByExample(exampleUser);

		// 1)
		if (CollectionUtil.isEmpty(users)) {
			throw new ValidatorException("User does not exist.");
		}

		User user = users.get(0);
		String resetPassword = Application.getMessage("usermgmt.resetLoginPassword");
		user.setPassword(resetPassword);
		userDao.update(user);

	}

	public void deleteUserRoles(Set<UserRole> userRoles) {
		// does not have to filter anymore because quipukit does the thing
		// userRoles = VoUtil.filterSelected(userRoles);
		userRoleDao.deleteAll(userRoles);
	}

	/**
	 * <pre>
	 * Validation: 
	 * 1) newPassword must same with retypeNewPassword. 
	 * 2) check whether username exist in db. 
	 * 3) currentPassword must same with password in db.
	 * 
	 * </pre>
	 * @param username -
	 *            user's username. login id.
	 * @param currentPassword -
	 *            not decoded
	 * @param newPassword -
	 *            not decoded
	 * @param retypeNewPassword -
	 * not decoded @
	 * @throws ValidatorException
	 */
	public void changePassword(String username, String currentPassword, String newPassword, String retypeNewPassword) {

		// 1
		if (!newPassword.equals(retypeNewPassword)) {
			throw new ValidatorException("User ID does not exist.");
		}

		User exampleUser = new User(false);
		exampleUser.setUsername(username);
		List<User> users = userDao.findByExample(exampleUser);
		// 2
		if (CollectionUtil.isEmpty(users))
			throw new ValidatorException("User ID does not exist.");

		User user = users.get(0);
		String encodedCurrentPassword = SecurityManagerImpl.encodeLoginPassword(currentPassword);
		// 3
		if (!user.getPassword().equals(encodedCurrentPassword))
			throw new ValidatorException("Invalid Current Password.");

		user.setPassword(newPassword);
		userDao.update(user);
	}

}
