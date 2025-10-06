package com.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.model.Customer;
import com.project.model.UserPrincipal;
import com.project.repository.CustomerRepo;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	private CustomerRepo customerRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Customer customer=customerRepo.findByEmail(email);
		
		if(customer == null) {
			System.out.println("User not found");
			throw new UsernameNotFoundException("User not found");
		}
		
		return new UserPrincipal(customer) ;
	}

}
