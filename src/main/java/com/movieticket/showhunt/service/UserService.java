package com.movieticket.showhunt.service;

import java.time.LocalDate;
import java.util.List;

import com.movieticket.showhunt.dto.BookingDTO;
import com.movieticket.showhunt.dto.MovieTimingsDTO;
import com.movieticket.showhunt.dto.PaymentConfirmationDTO;
import com.movieticket.showhunt.dto.PaymentRequestDTO;
import com.movieticket.showhunt.entity.Movie;
import com.movieticket.showhunt.entity.ScreenMovieMapping;
import com.movieticket.showhunt.entity.User;
import com.razorpay.Order;

import jakarta.mail.MessagingException;

public interface UserService
{
	public User checkLogin(String email, String password);
	public boolean verifyOtp(String email, String userOtp);
	public void sendOtpEmail(String toEmail, String name) throws MessagingException;
	public String addUser(User u);
	public List<Movie> getMovies();
	public MovieTimingsDTO getMovieTimings(int movieId);
	public ScreenMovieMapping getSeatsData(int mappingId, String showTime, LocalDate date);
	public void blockSeats(int mappingId, List<String> seatNumbers, LocalDate date);
	public void confirmPayment(PaymentConfirmationDTO dto) throws Exception;
	public Order createOrder(PaymentRequestDTO request) throws Exception;
	public List<Movie> getMoviesByCity(String city);
	public List<BookingDTO> getBookingsByEmail(String email);
	public String updateUser(User updatedUser);
}
