package com.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.dto.AdminDTO;
import com.project.dto.CustomerDTO;
import com.project.dto.PasswordUpdateDTO;
import com.project.model.Customer;
import com.project.security.SecurityUtil;
import com.project.services.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/customer")
public class CustomerController {

	@Autowired
	private CustomerService service;

	// register new customers
	@PostMapping("/register")
	public CustomerDTO postCustomer(@Valid @RequestBody Customer newCustomer) {
		return service.register(newCustomer);
	}

	// get all
	@GetMapping
	public Page<Customer> restGetAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue ="4") int size) {

		return service.getAll(page, size);
	}

	// get by id
	@GetMapping("/{id}")
	public Customer restGetById(@PathVariable Long id) {
		return service.getById(id);
	}

	// delete by id
	@DeleteMapping("/delete/{id}")
	public String deleteById(@PathVariable Long id) {

		return service.deleteCustomer(id);
	}

	// update by id
	@PatchMapping("/update/{id}")
	public Customer restUpdate(@PathVariable Long id, @RequestBody Customer updatedDetails) {

		Customer existingCustomer = service.getById(id);

		SecurityUtil.validateAccess(existingCustomer);

		return service.updateCustomer(id, updatedDetails);

	}

	// login
	@PostMapping("/login")
	public String login(@RequestBody Customer customer) {

		return service.verify(customer);
	}

	// ADMIN can add new-admin
	@PostMapping("/add-admin")
	public AdminDTO addAdmin(@RequestBody AdminDTO newAdmin) {
		return service.createAdmin(newAdmin);
	}

	// update password
	@PatchMapping("/updatePassword/{customerId}")
	public ResponseEntity<String> updatePassword(@PathVariable Long customerId, @RequestBody PasswordUpdateDTO dto) {
		service.resetPassword(customerId, dto);
		return ResponseEntity.ok("Password updated successfully");

	}
}
