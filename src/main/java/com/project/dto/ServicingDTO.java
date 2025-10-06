package com.project.dto;

import java.time.LocalDate;

public class ServicingDTO {

    private Long servicingId;
    private String serviceDescription;
    private LocalDate serviceDate;
    private Double servicingCost;
    private String remarks;
    private boolean serviceStatus;
    private boolean paymentStatus;

    public ServicingDTO() {}

    public ServicingDTO(Long servicingId, String serviceDescription, LocalDate serviceDate,
                            Double servicingCost, String remarks, boolean serviceStatus, boolean paymentStatus) {
    this.servicingId = servicingId;
    this.serviceDescription = serviceDescription;
    this.serviceDate = serviceDate;
    this.servicingCost = servicingCost;
    this.remarks = remarks;
    this.serviceStatus = serviceStatus;
    this.paymentStatus = paymentStatus;
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

	    public Double getServicingCost() {
	        return servicingCost;
	    }

	    public void setServicingCost(Double servicingCost) {
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
}

