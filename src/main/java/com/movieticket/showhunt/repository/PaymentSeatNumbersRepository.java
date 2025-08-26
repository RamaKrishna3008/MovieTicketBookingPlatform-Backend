package com.movieticket.showhunt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movieticket.showhunt.entity.PaymentSeatNumbers;

public interface PaymentSeatNumbersRepository extends JpaRepository<PaymentSeatNumbers, Integer> 
{
	
}
