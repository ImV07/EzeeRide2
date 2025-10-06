package com.project.dto;

public class CustomerDTO {

	
	private Long customerId;

	private String cname;


	public CustomerDTO() {}
	
	public CustomerDTO(Long customerId, String cname) {
		super();
		this.customerId = customerId;
		this.cname = cname;
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

	

	
}
