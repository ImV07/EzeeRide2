package com.project.dto;

import com.project.enums.FuelType;

public class VehicleDTO {

	private Long vehicleId;
    private String registrationNo;
    private String brand;
    private String model;
    private String type;
    private String color;
    private Integer capacity;
    private double chargePerDay;
    private FuelType fuelType;
    
    public VehicleDTO() {}
	public VehicleDTO(Long vehicleId, String registrationNo, String brand, String model, String type, String color,
			Integer capacity, double chargePerDay, FuelType fuelType) {
		super();
		this.vehicleId = vehicleId;
		this.registrationNo = registrationNo;
		this.brand = brand;
		this.model = model;
		this.type = type;
		this.color = color;
		this.capacity = capacity;
		this.chargePerDay = chargePerDay;
		this.fuelType = fuelType;
	}
	public Long getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
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
	public double getChargePerDay() {
		return chargePerDay;
	}
	public void setChargePerDay(double chargePerDay) {
		this.chargePerDay = chargePerDay;
	}
	public FuelType getFuelType() {
		return fuelType;
	}
	public void setFuelType(FuelType fuelType) {
		this.fuelType = fuelType;
	}
    
}
