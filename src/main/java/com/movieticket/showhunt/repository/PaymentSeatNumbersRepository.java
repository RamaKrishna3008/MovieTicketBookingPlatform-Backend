package com.movieticket.showhunt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movieticket.showhunt.entity.PaymentSeatNumbers;
import java.util.List;


public interface PaymentSeatNumbersRepository extends JpaRepository<PaymentSeatNumbers, Integer> 
{
	public List<PaymentSeatNumbers> findByPaymentId(int paymentId);
}
