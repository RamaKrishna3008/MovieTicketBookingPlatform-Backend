package com.movieticket.showhunt.dto;

import java.time.LocalDate;
import java.util.List;

public class PaymentConfirmationDTO 
{
	private String razorpayOrderId;
    private String razorpayPaymentId;
    private List<String> seatNumbers;
    private int mappingId;
    private LocalDate date;
	public String getRazorpayOrderId() {
		return razorpayOrderId;
	}
	public void setRazorpayOrderId(String razorpayOrderId) {
		this.razorpayOrderId = razorpayOrderId;
	}
	public String getRazorpayPaymentId() {
		return razorpayPaymentId;
	}
	public void setRazorpayPaymentId(String razorpayPaymentId) {
		this.razorpayPaymentId = razorpayPaymentId;
	}
	public List<String> getSeatNumbers() {
		return seatNumbers;
	}
	public void setSeatNumbers(List<String> seatNumbers) {
		this.seatNumbers = seatNumbers;
	}
	public int getMappingId() {
		return mappingId;
	}
	public void setMappingId(int mappingId) {
		this.mappingId = mappingId;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "PaymentConfirmationDTO [razorpayOrderId=" + razorpayOrderId + ", razorpayPaymentId=" + razorpayPaymentId
				+ ", seatNumbers=" + seatNumbers + ", mappingId=" + mappingId + ", date=" + date + "]";
	}
	
    
}
