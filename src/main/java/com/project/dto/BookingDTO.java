package com.project.dto;

import java.time.LocalDate;
import java.util.List;

public class BookingDTO {

    private Long bookingId;
    private Long customerId;
    private List<VehicleDTO> vehicles;
    private double amount;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;
    private String destination;
    private int groupSize;
    
    private String message;
    
    public BookingDTO() {}
 
	public BookingDTO(Long bookingId, Long customerId, List<VehicleDTO> vehicles, double amount, String status,
			LocalDate startDate, LocalDate endDate, String destination, int groupSize, String message) {
		super();
		this.bookingId = bookingId;
		this.customerId = customerId;
		this.vehicles = vehicles;
		this.amount = amount;
		this.status = status;
		this.startDate = startDate;
		this.endDate = endDate;
		this.destination = destination;
		this.groupSize = groupSize;
		this.message = message;
	}

	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public List<VehicleDTO> getVehicles() {
		return vehicles;
	}

	public void setVehicles(List<VehicleDTO> vehicles) {
		this.vehicles = vehicles;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public int getGroupSize() {
		return groupSize;
	}

	public void setGroupSize(int groupSize) {
		this.groupSize = groupSize;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
}
