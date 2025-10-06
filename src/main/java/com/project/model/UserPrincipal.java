package com.project.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.project.enums.Role;

public class UserPrincipal implements UserDetails{

	
	private static final long serialVersionUID = 1L;

	private Customer customer;
	
	public UserPrincipal(Customer customer) {
		this.customer=customer;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + customer.getRole().name())) ;
	}

	@Override
	public String getPassword() {
		return customer.getPassword();
	}

	@Override
	public String getUsername() {
		return customer.getEmail();
	}
	
	public Role getRole() {
	    return customer.getRole();
	}

	@Override
	public boolean isAccountNonExpired() {
	    return true;
	}

	@Override
	public boolean isAccountNonLocked() {
	    return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
	    return true;
	}

	@Override
	public boolean isEnabled() {
	    return true;
	}

	public Customer getCustomer() {
		return this.customer;
	}

}
