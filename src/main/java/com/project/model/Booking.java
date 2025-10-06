package com.project.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.enums.BookingStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;

@Entity
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookingId;

	private LocalDate dateOfBooking;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate startDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate endDate;


	@Enumerated(EnumType.STRING)
	private BookingStatus status=BookingStatus.PENDING;

	private double amount;
	
	private boolean paymentStatus=false;
	
	@Size(max=800)
	@Column(nullable = true)
	private String cancelReason;

	@ManyToOne
	@JoinColumn(name="customer_id",nullable = false)
	@JsonBackReference
	private Customer customer;
	
	@Column(nullable = true)
	private Integer groupSize;

	@Column(nullable = true)
	private String destination;

	@ManyToMany
	@JoinTable
		(
		 name = "booking_vehicle",
		 joinColumns = @JoinColumn(name = "booking_id"),
		 inverseJoinColumns = @JoinColumn(name = "vehicle_id", referencedColumnName = "vehicleId")
		 )
	private List<Vehicle> vehicle;
	
	public Booking() {}

	public Booking(Long bookingId, LocalDate dateOfBooking, LocalDate startDate, LocalDate endDate,
			BookingStatus status, double amount, boolean paymentStatus, @Size(max = 400) String cancelReason,
			Customer customer, Integer groupSize, String destination, List<Vehicle> vehicle) {
		super();
		this.bookingId = bookingId;
		this.dateOfBooking = dateOfBooking;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
		this.amount = amount;
		this.paymentStatus = paymentStatus;
		this.cancelReason = cancelReason;
		this.customer = customer;
		this.groupSize = groupSize;
		this.destination = destination;
		this.vehicle = vehicle;
	}

	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

	public LocalDate getDateOfBooking() {
		return dateOfBooking;
	}

	public void setDateOfBooking(LocalDate dateOfBooking) {
		this.dateOfBooking = dateOfBooking;
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

	public BookingStatus getStatus() {
		return status;
	}

	public void setStatus(BookingStatus status) {
		this.status = status;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public boolean isPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(boolean paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Integer getGroupSize() {
		return groupSize;
	}

	public void setGroupSize(Integer groupSize) {
		this.groupSize = groupSize;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public List<Vehicle> getVehicle() {
		return vehicle;
	}

	public void setVehicle(List<Vehicle> vehicle) {
		this.vehicle = vehicle;
	}

	@Override
	public String toString() {
		return "Booking [bookingId=" + bookingId + ", dateOfBooking=" + dateOfBooking + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", status=" + status + ", amount=" + amount + ", paymentStatus="
				+ paymentStatus + ", cancelReason=" + cancelReason + ", customer=" + customer + ", groupSize="
				+ groupSize + ", destination=" + destination + ", vehicle=" + vehicle + "]";
	}

	
	
}	