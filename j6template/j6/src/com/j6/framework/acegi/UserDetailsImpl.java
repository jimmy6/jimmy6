package com.j6.framework.acegi;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.userdetails.UserDetails;

import com.j6.framework.vo.VoBase;

public abstract class UserDetailsImpl extends VoBase implements UserDetails {

	private GrantedAuthority[] grantedAuthority;

	abstract public boolean isEnabled();

	abstract public String getPassword();

	abstract public String getUsername();

	public GrantedAuthority[] getAuthorities() {
		return grantedAuthority;
	}

	public void setAuthorities(GrantedAuthority... grantedAuthority) {
		this.grantedAuthority = grantedAuthority;
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

}
