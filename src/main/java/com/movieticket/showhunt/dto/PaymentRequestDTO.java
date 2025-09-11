package com.movieticket.showhunt.dto;

import java.time.LocalDate;
import java.util.List;

public class PaymentRequestDTO 
{
	 private int mappingId;
	    private List<String> seatNumbers;
	    private double amount;
	    private LocalDate date;
	    private String showTime;
	    private String email;
		public int getMappingId() {
			return mappingId;
		}
		public void setMappingId(int mappingId) {
			this.mappingId = mappingId;
		}
		public List<String> getSeatNumbers() {
			return seatNumbers;
		}
		public void setSeatNumbers(List<String> seatNumbers) {
			this.seatNumbers = seatNumbers;
		}
		public double getAmount() {
			return amount;
		}
		public void setAmount(double amount) {
			this.amount = amount;
		}
		public LocalDate getDate() {
			return date;
		}
		public void setDate(LocalDate date) {
			this.date = date;
		}
		public String getShowTime() {
			return showTime;
		}
		public void setShowTime(String showTime) {
			this.showTime = showTime;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
}

