package com.j6.framework.user.manager;

import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationException;
import org.acegisecurity.AuthenticationManager;
import org.acegisecurity.AuthenticationServiceException;
import org.acegisecurity.BadCredentialsException;
import org.acegisecurity.DisabledException;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.providers.encoding.Md5PasswordEncoder;

import com.j6.framework.exception.ValidatorException;
import com.j6.framework.user.vo.User;
import com.j6.framework.util.J;

public class SecurityManagerImpl implements SecurityManager {

	private AuthenticationManager authenticationManager;

	public SecurityManagerImpl() {
	}

	/**
	 * verify user login. return correct user object if correct.
	 * 
	 * Validation : 1) ERR0006 - Account inactivated. 2) ERR0005 - does not have any privilege to login even got
	 * account. 3,5) ERR0001 - username or password not correct. else authentication error 4) ERR0007 - else
	 * authentication error.
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws ValidatorException
	 */
	public User verifyLogin(String username, String password) {
		User retUser = null;

		Authentication authentication;

		UsernamePasswordAuthenticationToken userPasswordToken = new UsernamePasswordAuthenticationToken(username,
				encodeLoginPassword(password));

		try {
			authentication = authenticationManager.authenticate(userPasswordToken);
			// setAuthentication manually because i do not follow the whole authentication mechanism of acegi .
			SecurityContextHolder.getContext().setAuthentication(authentication);
			retUser = ((User) authentication.getPrincipal());

			// 1
		} catch (DisabledException e) {
			throw new ValidatorException("User ID does not exist.");

			// 3
		} catch (BadCredentialsException e) {
			J.printNegetif(".............");
			throw new ValidatorException("Username or password wrong.");

			// 5 this exception is system error. current it just occur when username not in db so i just
			// solve it by throw username and password wrong.
		} catch (AuthenticationServiceException e) {
			J.printNegetif("......sssssssssssssssssss.......");
			throw new ValidatorException("Username or password wrong.");
			// 4
		} catch (AuthenticationException e) {
			throw new ValidatorException("Can not log in at this moment. Please contact administrator.");
		}

		// 2
		if (retUser.getAuthorities() == null || retUser.getAuthorities().length == 0) {
			throw new ValidatorException("You do not have privilege to login.");
		}

		return retUser;
	}

	public static String encodeLoginPassword(String password) {
		return new Md5PasswordEncoder().encodePassword(password, null);
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

}