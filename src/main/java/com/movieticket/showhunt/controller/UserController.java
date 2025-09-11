package com.movieticket.showhunt.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.movieticket.showhunt.dto.BlockSeatRequest;
import com.movieticket.showhunt.dto.MovieTimingsDTO;
import com.movieticket.showhunt.dto.PaymentConfirmationDTO;
import com.movieticket.showhunt.dto.PaymentRequestDTO;
import com.movieticket.showhunt.dto.ScreenMovieMappingResponseDTO;
import com.movieticket.showhunt.entity.Movie;
import com.movieticket.showhunt.entity.ScreenMovieMapping;
import com.movieticket.showhunt.entity.User;
import com.movieticket.showhunt.service.UserService;
import com.razorpay.Order;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("user")
public class UserController 
{
	@Autowired
	private UserService service;
	
	@PostMapping("/sendotp")
    public ResponseEntity<String> sendOtp(@RequestBody User user) {
        try {
        	System.out.println(user.getEmail());
            service.sendOtpEmail(user.getEmail(), user.getName());
            return ResponseEntity.ok("OTP sent successfully!");
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send OTP: " + e.getMessage());
        }
    }

    @PostMapping("/verifyotp")
    public ResponseEntity<String> verifyOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otp = request.get("otp");

        if (service.verifyOtp(email, otp)) {
            return ResponseEntity.ok("OTP verified successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP");
        }
    }
    @PostMapping("/adduser")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        try
        {
        	return ResponseEntity.status(HttpStatus.CREATED).body(service.addUser(user));
        }
        catch (Exception e) {
        	return ResponseEntity.status(500).body("Failed To Add");
		}
    }
    @GetMapping("/home")
    public ResponseEntity<List<Movie>> getActiveMovies(@RequestParam(required = false) String city) {
        List<Movie> movies;

        if (city != null && !city.isEmpty()) {
            movies = service.getMoviesByCity(city);  
        } else {
            movies = service.getMovies();  
        }

        if (movies.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(movies);
    }

    
    @GetMapping("/movie/timings/{id}")
    public ResponseEntity<?> getMovieTimings(@PathVariable int id) {
        try {
            MovieTimingsDTO dto = service.getMovieTimings(id);
            return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
        	System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("error", "Something went wrong"));
        }
    }
    @GetMapping("/movie/seats/{mappingId}/{showTime}/{date}")
    public ResponseEntity<?> getSeatsData(
            @PathVariable int mappingId,
            @PathVariable String showTime,
            @PathVariable LocalDate date) 
    {
    	try
    	{
    		ScreenMovieMapping s=service.getSeatsData(mappingId,showTime,date);
    		ScreenMovieMappingResponseDTO res=new ScreenMovieMappingResponseDTO(s, s.getSeats());
    		return ResponseEntity.ok(res);
    	}
    	catch (Exception e) 
    	{
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed To Fetch");
		}
    }

    @PostMapping("/block-seats")
    public ResponseEntity<String> blockSeats(@RequestBody BlockSeatRequest request) {
        service.blockSeats(request.getMappingId(), request.getSeatNumbers(), request.getDate());
        return ResponseEntity.ok("Seats blocked successfully");
    }
    @PostMapping(value = "/create-order", produces = "application/json")
    public ResponseEntity<Map<String, Object>> createPaymentOrder(@RequestBody PaymentRequestDTO request) {
        try {
            Order order = service.createOrder(request);

            Map<String, Object> response = new HashMap<>();
            response.put("orderId", order.get("id"));
            response.put("amount", request.getAmount());
            response.put("currency", "INR");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
    
    @PostMapping(value = "/payment/confirm", produces = "application/json")
    public ResponseEntity<Map<String, String>> confirmPayment(@RequestBody PaymentConfirmationDTO dto) {
    	try {
    		System.out.println(dto);
    		service.confirmPayment(dto);
    		return ResponseEntity.ok(Map.of("message", "Payment confirmed & seats booked"));
    		} catch (Exception e) {
    		return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
    		}
    }
    
    @GetMapping("/viewMyBookings")
    public ResponseEntity<?> getBookingsByEmail(@RequestParam String email) {
    	try {
        return ResponseEntity.ok(service.getBookingsByEmail(email));
    	}
    	catch (Exception e) {
    		return ResponseEntity.status(500).body("Failed To Fetch");
		}
    }
    @PutMapping("/updateuser")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
    	try {
            return ResponseEntity.ok(service.updateUser(user));
        	}
        	catch (Exception e) {
        		return ResponseEntity.status(500).body("Failed To Updated");
    		}
    }


}
