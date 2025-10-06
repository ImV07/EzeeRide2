package com.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.project.enums.Role;

public class AdminDTO {
	
	private Long id;
	private String cname;
	private String email;
	@JsonProperty(access = Access.READ_ONLY)
	private Role role=Role.ADMIN;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	public AdminDTO() {}
	public AdminDTO(Long id, String cname, String email, Role role,String password) {
		super();
		this.id = id;
		this.cname = cname;
		this.email = email;
		this.role = role;
		this.password = password;
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	
	
}
