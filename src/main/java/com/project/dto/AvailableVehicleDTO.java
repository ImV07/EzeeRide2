package com.project.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AvailableVehicleDTO {
	
	@JsonProperty("Your Booking-Id")
	private Long bookingId;
	
	@JsonProperty("Your destination")
	private String destination;
	
	@JsonProperty("available Vehicles")
	private List<VehicleDTO> availableVehicles;
	
	@JsonProperty("message")
	private String message;
	
	public AvailableVehicleDTO() {}

	public AvailableVehicleDTO(Long bookingId,String destination, List<VehicleDTO> availableVehicles, String message) {
		super();
		this.bookingId=bookingId;
		this.destination = destination;
		this.availableVehicles = availableVehicles;
		this.message = message;
	}
	
	public Long getBookingId() {
		return bookingId;
	}
	
	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public List<VehicleDTO> getAvailableVehicles() {
		return availableVehicles;
	}

	public void setAvailableVehicles(List<VehicleDTO> availableVehicles) {
		this.availableVehicles = availableVehicles;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
