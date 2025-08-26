package com.movieticket.showhunt.dto;

import java.time.LocalDate;
import java.util.List;

public class BlockSeatRequest {
    private int mappingId;
    private List<String> seatNumbers;
    private LocalDate date;
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
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
}

