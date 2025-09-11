package com.movieticket.showhunt.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "payment_seat_numbers")
public class PaymentSeatNumbers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "payment_id")
    private int paymentId;

    @Column(name = "seat_number")
    private String seatNumber;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public String getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

    
}

