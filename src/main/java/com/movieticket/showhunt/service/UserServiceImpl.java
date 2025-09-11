package com.movieticket.showhunt.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.movieticket.showhunt.dto.BookingDTO;
import com.movieticket.showhunt.dto.DateTimingsDTO;
import com.movieticket.showhunt.dto.MovieTimingsDTO;
import com.movieticket.showhunt.dto.PaymentConfirmationDTO;
import com.movieticket.showhunt.dto.PaymentRequestDTO;
import com.movieticket.showhunt.dto.ShowTimeDTO;
import com.movieticket.showhunt.entity.Movie;
import com.movieticket.showhunt.entity.Payment;
import com.movieticket.showhunt.entity.PaymentSeatNumbers;
import com.movieticket.showhunt.entity.ScreenMovieMapping;
import com.movieticket.showhunt.entity.Seat;
import com.movieticket.showhunt.entity.SeatStatus;
import com.movieticket.showhunt.entity.User;
import com.movieticket.showhunt.repository.MovieRepository;
import com.movieticket.showhunt.repository.PaymentRepository;
import com.movieticket.showhunt.repository.PaymentSeatNumbersRepository;
import com.movieticket.showhunt.repository.ScreenMovieMappingRepository;
import com.movieticket.showhunt.repository.SeatRepository;
import com.movieticket.showhunt.repository.UserRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UserServiceImpl implements UserService
{
	@Autowired
	private UserRepository userRepository;
	@Autowired
    private JavaMailSender mailSender;
	@Autowired
    private ScreenMovieMappingRepository mappingRepository;
	@Autowired
    private MovieRepository movieRepository;
	@Autowired
    private SeatRepository seatRepository;
	
	@Autowired
    private PaymentRepository paymentRepository;
	@Autowired
    private PaymentSeatNumbersRepository numbersRepository;

    @Autowired
    private PaymentSeatNumbersRepository seatNumbersRepository;
    @Value("${razorpay.key}")
	private String RAZORPAY_KEY;
    @Value("${razorpay.secret}")
	private String RAZORPAY_SECRET;
    
    private final Map<String, String> otpStorage = new HashMap<>();

    @Value("${spring.mail.username}")
    private String fromEmail;
	
	@Override
	public User checkLogin(String email,String password)
	{
		return userRepository.findByEmailAndPassword(email, password);
	}
	@Override
	public String addUser(User u)
	{
		userRepository.save(u);
		return "User Inserted Successfully";
	}
    public String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
    @Override
    public void sendOtpEmail(String toEmail, String name) throws MessagingException {
        String otp = generateOtp();
        otpStorage.put(toEmail, otp);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(fromEmail);
        helper.setTo(toEmail);
        helper.setSubject("Your OTP for ShowHunt Registration");

        String htmlContent = """
                <div style="font-family: Arial, sans-serif; padding: 20px; background: #f8f9fa; border-radius: 10px;">
                    <h2 style="color: #007BFF;">Hello, %s!</h2>
                    <p style="font-size: 16px; color: #333;">
                        Thank you for registering with <strong>ShowHunt</strong>.<br>
                        Your One-Time Password (OTP) for email verification is:
                    </p>
                    <div style="text-align: center; margin: 20px 0;">
                        <span style="font-size: 24px; font-weight: bold; color: #28a745; padding: 10px 20px; border: 2px dashed #28a745; border-radius: 8px;">%s</span>
                    </div>
                    <p style="font-size: 14px; color: #555;">
                        This OTP will expire in 5 minutes. Please do not share this code with anyone.
                    </p>
                    <p style="color: #dc3545;">If you did not request this, please ignore this email.</p>
                </div>
                """.formatted(name, otp);

        helper.setText(htmlContent, true);
        mailSender.send(message);
    }
    @Override
    public boolean verifyOtp(String email, String userOtp) {
        String storedOtp = otpStorage.get(email);
        if (storedOtp != null && storedOtp.equals(userOtp)) {
            otpStorage.remove(email); 
            return true;
        }
        return false;
    }
    @Override
    public MovieTimingsDTO getMovieTimings(int movieId) {
        LocalDate today = LocalDate.now();
        List<ScreenMovieMapping> mappings = mappingRepository.findActiveMappingsByMovieId(movieId, today);

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new EntityNotFoundException("Movie not found"));

        Map<LocalDate, List<ShowTimeDTO>> grouped = new HashMap<>();

        for (ScreenMovieMapping mapping : mappings) {
            LocalDate start = mapping.getFromDate().isBefore(today) ? today : mapping.getFromDate();
            LocalDate end = mapping.getExpiryDate();

            for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
                Double occupancy = seatRepository.findAvailableSeatsCountForDate(mapping.getId(), date);

                System.out.println("Date: " + date + " | Occupancy: " + occupancy * 100 );

                ShowTimeDTO showTimeDTO = new ShowTimeDTO(mapping, occupancy != null ? (int) (occupancy * 100) : 0);

                grouped.computeIfAbsent(date, k -> new ArrayList<>()).add(showTimeDTO);
            }
        }

        List<DateTimingsDTO> timings = grouped.entrySet().stream()
                .map(entry -> new DateTimingsDTO(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(DateTimingsDTO::getDate))
                .toList();

        return new MovieTimingsDTO(movie, timings);
    }



	@Override
	public List<Movie> getMovies() 
	{
		return mappingRepository.findDistinctMoviesWithActiveMappings(LocalDate.now());

	}
	@Override
	public List<Movie> getMoviesByCity(String city) 
	{
		return mappingRepository.findDistinctMoviesWithCity(LocalDate.now(), city);

	}
	@Override
	public ScreenMovieMapping getSeatsData(int mappingId, String showTime, LocalDate date) 
	{
		 ScreenMovieMapping mapping = mappingRepository.findById(mappingId).get();
		 List<Seat> filteredSeats = seatRepository.findByMappingAndDateAndShowTime(mappingId, date, showTime);
		 mapping.setSeats(filteredSeats);
		return mapping;
	}
	@Override
	public void blockSeats(int mappingId, List<String> seatNumbers, LocalDate date) {
        List<Seat> seats = seatRepository.findByMappingIdAndDateAndSeatNumberIn(mappingId, date, seatNumbers);

        for (Seat seat : seats) {
            if (!seat.getStatus().equals(SeatStatus.BOOKED)) {
                seat.setStatus(SeatStatus.BLOCKED);
            }
        }

        seatRepository.saveAll(seats);
    }
	
	 public void bookSeats(int mappingId, List<String> seatNumbers, LocalDate date) throws Exception {
	        List<Seat> seatsToBook = seatRepository.findByMappingIdAndDateAndSeatNumberIn(mappingId, date, seatNumbers);

	        if (seatsToBook.size() != seatNumbers.size()) {
	            throw new Exception("Some seats are already booked or invalid.");
	        }

	        for (Seat seat : seatsToBook) {
	            if (SeatStatus.BOOKED.equals(seat.getStatus())) {
	                throw new Exception("Seat " + seat.getSeatNumber() + " is already booked.");
	            }
	            seat.setStatus(SeatStatus.BOOKED);
	        }

	        seatRepository.saveAll(seatsToBook);
	    }
	

	 
	 @Override
	 public Order createOrder(PaymentRequestDTO request) throws Exception {

	     blockSeats(request.getMappingId(), request.getSeatNumbers(), request.getDate());

	     RazorpayClient client = new RazorpayClient(RAZORPAY_KEY, RAZORPAY_SECRET);

	     org.json.JSONObject orderRequest = new org.json.JSONObject();
	     orderRequest.put("amount", request.getAmount() ); 
	     orderRequest.put("currency", "INR");
	     orderRequest.put("receipt", "order_rcptid_" + System.currentTimeMillis());

	     Order razorpayOrder = client.orders.create(orderRequest);

	     Payment payment = new Payment();
	     payment.setAmount(request.getAmount());
	     payment.setEmail(request.getEmail());
	     payment.setPaymentTime(LocalDateTime.now());
	     payment.setRazorpayOrderId(razorpayOrder.get("id")); 
	     payment.setMappingId(request.getMappingId());
	     payment.setShowDate(request.getDate());
	     payment.setShowTime(request.getShowTime());
	     payment.setStatus("PENDING"); 
	     paymentRepository.save(payment);

	     for (String seat : request.getSeatNumbers()) {
	         PaymentSeatNumbers ps = new PaymentSeatNumbers();
	         ps.setPaymentId(payment.getId());
	         ps.setSeatNumber(seat);
	         seatNumbersRepository.save(ps);
	     }

	     return razorpayOrder;
	 }
	 @Override
	 public void confirmPayment(PaymentConfirmationDTO dto) throws Exception {

	     RazorpayClient client = new RazorpayClient(RAZORPAY_KEY, RAZORPAY_SECRET);
	     com.razorpay.Payment razorpayPayment = client.payments.fetch(dto.getRazorpayPaymentId());

	     if (!"captured".equals(razorpayPayment.get("status"))) {
	         throw new Exception("Payment not successful in Razorpay");
	     }

	     Payment payment = paymentRepository.findByRazorpayOrderId(dto.getRazorpayOrderId());

	     payment.setStatus("PAID");
	     payment.setRazorpayPaymentId(dto.getRazorpayPaymentId());
	     paymentRepository.save(payment);

	     bookSeats(dto.getMappingId(), dto.getSeatNumbers(), dto.getDate());

	     sendEmail(payment.getEmail(), payment, dto.getSeatNumbers());
	 }


	    private void sendEmail(String to, Payment payment, List<String> seats) throws Exception {
	        MimeMessage message = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);

	        helper.setTo(to);
	        helper.setSubject("Movie Ticket Booking Confirmation");
	        String seatList = String.join(", ", seats);
	        helper.setText(
	            "<h3>Your booking is confirmed!</h3>" +
	            "<p>Payment ID: " + payment.getRazorpayPaymentId() + "</p>" +
	            "<p>Amount: ₹" + payment.getAmount() + "</p>" +
	            "<p>Show Date: " + payment.getShowDate() + "</p>" +
	            "<p>Show Time: " + payment.getShowTime() + "</p>" +
	            "<p>Seats: " + seatList + "</p>", true
	        );

	        mailSender.send(message);
	    }
	    private BookingDTO toDTO(Payment payment) {
	        BookingDTO dto = new BookingDTO();

	        dto.setId(payment.getId());
	        dto.setAmount(payment.getAmount());
	        dto.setEmail(payment.getEmail());
	        dto.setPaymentTime(payment.getPaymentTime());
	        dto.setRazorpayOrderId(payment.getRazorpayOrderId());
	        dto.setRazorpayPaymentId(payment.getRazorpayPaymentId());
	        dto.setShowDate(payment.getShowDate());
	        dto.setShowTime(payment.getShowTime());
	        dto.setStatus(payment.getStatus());

	        ScreenMovieMapping mapping=mappingRepository.findById(payment.getMappingId()).get();
	        dto.setScreenName(mapping.getScreen().getName());
	        dto.setMovieName(mapping.getMovie().getName());
	        dto.setMoviePoster(mapping.getMovie().getMovieposter());
	        
	        List<PaymentSeatNumbers> seatnumbers=numbersRepository.findByPaymentId(payment.getId());
	        if (seatnumbers != null) {
	            dto.setSeatNumbers(
	                seatnumbers
	                       .stream()
	                       .map(PaymentSeatNumbers::getSeatNumber)
	                       .collect(Collectors.toList())
	            );
	        }

	        return dto;
	    }
	     
	    @Override
	    public List<BookingDTO> getBookingsByEmail(String email) {
	        return paymentRepository.findByEmail(email)
	                .stream()
	                .map(this::toDTO) 
	                .collect(Collectors.toList());
	    }
	    @Override
	    public String updateUser(User updatedUser) {
	        Optional<User> existingUserOpt = userRepository.findById(updatedUser.getId());

	        if (existingUserOpt.isPresent()) {
	            User existingUser = existingUserOpt.get();

	            existingUser.setName(updatedUser.getName());
	            existingUser.setContact(updatedUser.getContact());
	            existingUser.setPassword(updatedUser.getPassword());

	            return "User Updated Successfully";
	        } else {
	            throw new RuntimeException("User not found with ID: " + updatedUser.getId());
	        }
	    }
	
}
