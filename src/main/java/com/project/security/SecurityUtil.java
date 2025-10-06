package com.project.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.project.model.Customer;
import com.project.model.UserPrincipal;

public class SecurityUtil {

	 public static void validateAccess(Customer resourceOwner) {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

	        String loggedInEmail = userPrincipal.getUsername();
	        String role = userPrincipal.getRole().name();

	        if (!loggedInEmail.equals(resourceOwner.getEmail()) && !role.equals("ADMIN")) {
	            throw new SecurityException("Access denied: you can only access your own data or must be an admin.");
	        }
	    }
	
}