package com.movieticket.showhunt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movieticket.showhunt.entity.Payment;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> 
{
	public Payment findByRazorpayOrderId(String razorpayOrderId);
	
	public List<Payment> findByEmail(String email);
}
