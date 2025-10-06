package com.project.dto;


public class DashboardDTO {

	private Long vehicleId;
	private String model;
	private double earning;
	private double servicingCost;
	private double totalEarning;
	
	public DashboardDTO() {}
	
	public DashboardDTO(Long vehicleId, String model, double earning, double servicingCost, double totalEarning) {
		super();
		this.vehicleId = vehicleId;
		this.model = model;
		this.earning = earning;
		this.servicingCost = servicingCost;
		this.totalEarning = totalEarning;
	}

	public Long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public double getEarning() {
		return earning;
	}

	public void setEarning(double earning) {
		this.earning = earning;
	}

	public double getServicingCost() {
		return servicingCost;
	}

	public void setServicingCost(double servicingCost) {
		this.servicingCost = servicingCost;
	}

	public double getTotalEarning() {
		return totalEarning;
	}

	public void setTotalEarning(double totalEarning) {
		this.totalEarning = totalEarning;
	}
	
	
}
