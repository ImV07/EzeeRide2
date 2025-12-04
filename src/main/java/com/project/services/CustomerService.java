package com.project.services;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.dto.AdminDTO;
import com.project.dto.CustomerDTO;
import com.project.dto.PasswordUpdateDTO;
import com.project.enums.Role;
import com.project.exception.BadRequestException;
import com.project.exception.ResourceNotFound;
import com.project.model.Customer;
import com.project.repository.CustomerRepo;

import jakarta.annotation.PostConstruct;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private JWTService jwtService;

	@Autowired
	private ModelMapper modelMapper;

	private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

//////////////////////

	// save customer
	public CustomerDTO register(Customer newCustomer) {

		try {
			String encodedPassword = passwordEncoder.encode(newCustomer.getPassword());
			newCustomer.setPassword(encodedPassword);
			newCustomer.setRole(Role.USER);
			customerRepo.save(newCustomer);
		}
		catch (Exception e) {
			throw new BadRequestException("check EmailFormat/Contact No.{10}");
		}
		logger.info("New customer saved with Id:"+newCustomer.getCustomerId());

		return modelMapper.map(newCustomer, CustomerDTO.class);
	}

//////////////////////

	// get all
	public Page<Customer> getAll(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return customerRepo.findAll(pageable);
	}

//////////////////////

	// get by id
	public Customer getById(Long id) {
		return customerRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Customer not found with id: " + id));
	}

//////////////////////

	// delete customer
	public String deleteCustomer(Long id) {
		Customer tempCustomer = customerRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFound("Customer not found with id: " + id));

		customerRepo.delete(tempCustomer);

		logger.info("customer deleted with id: " + tempCustomer.getCustomerId());

		return "customer deleted with id " + id;
	}

//////////////////////

	// update details
	public Customer updateCustomer(Long id, Customer newDetails) {

		Customer existingData = customerRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFound("customer not exist with id: " + id));

		if (newDetails.getEmail() != null || newDetails.getPassword() != null || newDetails.getRole() != null) {
			throw new BadRequestException("Only 'cname', 'contact' can be updated here.");
		}

		if (newDetails.getCname() != null) {
			existingData.setCname(newDetails.getCname());
		}

		if (newDetails.getContact() != null) {
			existingData.setContact(newDetails.getContact());
		}

		logger.info("customer with id: " + existingData.getCustomerId() + " updates details !!!");

		return customerRepo.save(existingData);
	}

//////////////////////

	// verify customer
	public String verify(Customer customer) {

		Authentication authentication = authManager
				.authenticate(new UsernamePasswordAuthenticationToken(customer.getEmail(), customer.getPassword()));
		if (authentication.isAuthenticated()) {
			return jwtService.generateToken(customer.getEmail());
		}
		return "Login Failed !";
	}

//////////////////////

	// Add new admin
	public AdminDTO createAdmin(AdminDTO newAdmin) {
		Customer admin = new Customer();
		admin.setCname(newAdmin.getCname());
		admin.setEmail(newAdmin.getEmail());
		admin.setPassword(passwordEncoder.encode(newAdmin.getPassword()));
		admin.setRole(Role.ADMIN);
		customerRepo.save(admin);
		logger.info("New ADMIN saved with Id:"+admin.getCustomerId());

		return modelMapper.map(admin, AdminDTO.class);

	}

	@PostConstruct
	public void init() {
		String Email = "admin1@gmail.com";
		Customer existing = customerRepo.findByEmail(Email);

		if (existing == null) {
			AdminDTO admin = new AdminDTO();
			admin.setCname("Admin1");
			admin.setEmail(Email);
			admin.setPassword("admin1");
			createAdmin(admin);
            logger.info("Admin created...");
        } else {
            logger.info("Admin present...");
		}

	}

//////////////////////

	// update password
	public void resetPassword(Long customerId, PasswordUpdateDTO dto) {

		Customer customer = customerRepo.findById(customerId)
				.orElseThrow(() -> new ResourceNotFound("customer not exist with this id."));

		if (!customer.getEmail().equalsIgnoreCase(dto.getEmail())) {
			throw new BadRequestException("Entered email does not match with registered email");
		}

		customer.setPassword(passwordEncoder.encode(dto.getNewRawPassword()));

		customerRepo.save(customer);

	}

}
