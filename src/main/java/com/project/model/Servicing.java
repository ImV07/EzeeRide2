package com.project.model;

import java.time.LocalDate;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Servicing {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long servicingId;
	
	@Column(nullable = false)
	private String serviceDescription;
	
	private LocalDate serviceDate=LocalDate.now();
	
	@Column(nullable = false)
	private double servicingCost;
	
	private String remarks;
	
	@Column
	private boolean serviceStatus=true;
	
	@Column
	private boolean paymentStatus=false;
	
	
	@ManyToOne
	@JoinColumn(name = "vehicle_id",referencedColumnName = "vehicleId",nullable = false)
	private Vehicle vehicle;
	
	public Servicing() {}
	
	public Servicing(Long servicingId, String serviceDescription, LocalDate serviceDate, double servicingCost,
			String remarks, boolean serviceStatus, boolean paymentStatus, Vehicle vehicles) {
		super();
		this.servicingId = servicingId;
		this.serviceDescription = serviceDescription;
		this.serviceDate = serviceDate;
		this.servicingCost = servicingCost;
		this.remarks = remarks;
		this.serviceStatus = serviceStatus;
		this.paymentStatus = paymentStatus;
		this.vehicle = vehicles;
	}

	public Long getServicingId() {
		return servicingId;
	}

	public void setServicingId(Long servicingId) {
		this.servicingId = servicingId;
	}

	public String getServiceDescription() {
		return serviceDescription;
	}

	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
	}

	public LocalDate getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(LocalDate serviceDate) {
		this.serviceDate = serviceDate;
	}

	public double getServicingCost() {
		return servicingCost;
	}

	public void setServicingCost(double servicingCost) {
		this.servicingCost = servicingCost;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public boolean isServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(boolean serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public boolean isPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(boolean paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Vehicle getVehicles() {
		return vehicle;
	}

	public void setVehicles(Vehicle vehicles) {
		this.vehicle = vehicles;
	}

	@Override
	public String toString() {
		return "Servicing [servicingId=" + servicingId + ", serviceDescription=" + serviceDescription + ", serviceDate="
				+ serviceDate + ", servicingCost=" + servicingCost + ", remarks=" + remarks + ", serviceStatus="
				+ serviceStatus + ", paymentStatus=" + paymentStatus + ", vehicles=" + vehicle + "]";
	}

	
	
	
}
