package com.project.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.enums.FuelType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "vehicle")
public class Vehicle {

	@Id
	@SequenceGenerator(name = "vehicle_seq", sequenceName = "vehicle_sequence", initialValue = 1001, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "vehicle_seq")
	private Long vehicleId;

	private String city;

	@Column(unique = true, nullable = false)
	private String registrationNo;

	private String brand;
	private String model;
	private String type;
	private String color;

	private Integer capacity;

	private Integer yearOfManufacture;

	private boolean available = true;

	private double chargePerDay;

	private String vehicleCondition;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate insuranceDate;

	@Enumerated(EnumType.STRING)
	private FuelType fuelType;

	private double providedKm;

	@JsonIgnore
	@ManyToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
	private List<Booking> booking;

	@JsonIgnore
	@OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
	private List<Servicing> servicing;

	public Vehicle() {
	}

	public Vehicle(Long vehicleId, String city, String registrationNo, String brand, String model, String type,
			String color, Integer capacity, Integer yearOfManufacture, boolean available, double chargePerDay,
			String vehicleCondition, LocalDate insuranceDate, FuelType fuelType, double providedKm,
			List<Booking> booking, List<Servicing> servicing) {
		super();
		this.vehicleId = vehicleId;
		this.city = city;
		this.registrationNo = registrationNo;
		this.brand = brand;
		this.model = model;
		this.type = type;
		this.color = color;
		this.capacity = capacity;
		this.yearOfManufacture = yearOfManufacture;
		this.available = available;
		this.chargePerDay = chargePerDay;
		this.vehicleCondition = vehicleCondition;
		this.insuranceDate = insuranceDate;
		this.fuelType = fuelType;
		this.providedKm = providedKm;
		this.booking = booking;
		this.servicing = servicing;
	}

	public Long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegistrationNo() {
		return registrationNo;
	}

	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public Integer getYearOfManufacture() {
		return yearOfManufacture;
	}

	public void setYearOfManufacture(Integer yearOfManufacture) {
		this.yearOfManufacture = yearOfManufacture;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public double getChargePerDay() {
		return chargePerDay;
	}

	public void setChargePerDay(double chargePerDay) {
		this.chargePerDay = chargePerDay;
	}

	public String getVehicleCondition() {
		return vehicleCondition;
	}

	public void setVehicleCondition(String vehicleCondition) {
		this.vehicleCondition = vehicleCondition;
	}

	public LocalDate getInsuranceDate() {
		return insuranceDate;
	}

	public void setInsuranceDate(LocalDate insuranceDate) {
		this.insuranceDate = insuranceDate;
	}

	public FuelType getFuelType() {
		return fuelType;
	}

	public void setFuelType(FuelType fuelType) {
		this.fuelType = fuelType;
	}

	public double getProvidedKm() {
		return providedKm;
	}

	public void setProvidedKm(double providedKm) {
		this.providedKm = providedKm;
	}

	public List<Booking> getBooking() {
		return booking;
	}

	public void setBooking(List<Booking> booking) {
		this.booking = booking;
	}

	public List<Servicing> getServicing() {
		return servicing;
	}

	public void setServicing(List<Servicing> servicing) {
		this.servicing = servicing;
	}

	@Override
	public String toString() {
		return "Vehicle [vehicleId=" + vehicleId + ", city=" + city + ", registrationNo=" + registrationNo + ", brand="
				+ brand + ", model=" + model + ", type=" + type + ", color=" + color + ", capacity=" + capacity
				+ ", yearOfManufacture=" + yearOfManufacture + ", available=" + available + ", chargePerDay="
				+ chargePerDay + ", vehicleCondition=" + vehicleCondition + ", insuranceDate=" + insuranceDate
				+ ", fuelType=" + fuelType + ", providedKm=" + providedKm + ", booking=" + booking + ", servicing="
				+ servicing + "]";
	}
	
	@PrePersist
    @PreUpdate
    private void formatFieldsToUpper() {
        if (this.city != null) this.city = this.city.toUpperCase();
        if (this.registrationNo != null) this.registrationNo = this.registrationNo.toUpperCase();
        if (this.brand != null) this.brand = this.brand.toUpperCase();
        if (this.model != null) this.model = this.model.toUpperCase();
        if (this.type != null) this.type = this.type.toUpperCase();
        if (this.color != null) this.color = this.color.toUpperCase();
        if (this.vehicleCondition != null) this.vehicleCondition = this.vehicleCondition.toUpperCase();
    }

}
