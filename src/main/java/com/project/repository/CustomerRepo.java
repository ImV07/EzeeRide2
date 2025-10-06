package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.model.Customer;


@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long>  {

	Customer findByEmail(String email);
	
	Customer findByCname(String cname);
	
}
