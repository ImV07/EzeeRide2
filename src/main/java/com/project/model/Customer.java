package com.project.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.project.enums.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long customerId;

	@Column(nullable = false)
	private String cname;

	@Email
	@Column(unique = true)
	private String email;

	@Enumerated(EnumType.STRING)
	private Role role;

	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(nullable = false)
	private String password;

	@Pattern(regexp = "[0-9]{10}", message = "Invalid contact format. Please provide a valid 10-digit mobile number.")
	@Column(unique=true ,nullable = true)
	private String contact;

	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Booking> booking;

	public Customer() {
	}

	public Customer(Long customerId, String cname, @Email String email, Role role, String password, String contact,
			List<Booking> booking) {
		super();
		this.customerId = customerId;
		this.cname = cname;
		this.email = email;
		this.role = role;
		this.password = password;
		this.contact = contact;
		this.booking = booking;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public List<Booking> getBooking() {
		return booking;
	}

	public void setBooking(List<Booking> booking) {
		this.booking = booking;
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", cname=" + cname + ", email=" + email + ", role=" + role
				+ ", password=" + password + ", contact=" + contact + ", booking=" + booking + "]";
	}

}
