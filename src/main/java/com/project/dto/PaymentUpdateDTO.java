package com.project.dto;

public class PaymentUpdateDTO {
	
    private boolean paymentStatus;
    private String cancelReason;
    
    public PaymentUpdateDTO() {}
    
	public PaymentUpdateDTO(boolean paymentStatus, String cancelReason) {
		super();
		this.paymentStatus = paymentStatus;
		this.cancelReason = cancelReason;
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
    
    
}
