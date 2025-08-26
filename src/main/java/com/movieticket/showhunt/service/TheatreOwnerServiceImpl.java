package com.movieticket.showhunt.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movieticket.showhunt.dto.ScreenMovieMappingRequest;
import com.movieticket.showhunt.entity.Movie;
import com.movieticket.showhunt.entity.Screen;
import com.movieticket.showhunt.entity.ScreenMovieMapping;
import com.movieticket.showhunt.entity.Seat;
import com.movieticket.showhunt.entity.SeatStatus;
import com.movieticket.showhunt.entity.TheatreOwner;
import com.movieticket.showhunt.repository.MovieRepository;
import com.movieticket.showhunt.repository.ScreenMovieMappingRepository;
import com.movieticket.showhunt.repository.ScreenRepository;
import com.movieticket.showhunt.repository.SeatRepository;
import com.movieticket.showhunt.repository.TheatreOwnerRepository;

@Service
public class TheatreOwnerServiceImpl implements TheatreOwnerService
{
	@Autowired
	private TheatreOwnerRepository ownerRepository;
	@Autowired
    private ScreenRepository screenRepository;
	@Autowired
    private MovieRepository movieRepository;
	@Autowired
    private ScreenMovieMappingRepository mappingRepository;
    @Autowired
    private SeatRepository seatRepository;
	
	@Override
	public TheatreOwner checkLogin(String username,String password)
	{
		return ownerRepository.findByUsernameAndPassword(username, password);
	}  
	
	@Override
    public Screen addScreen(int theatreOwnerId, Screen screen) {
        TheatreOwner owner = ownerRepository.findById(theatreOwnerId)
                .orElseThrow(() -> new RuntimeException("TheatreOwner not found with ID: " + theatreOwnerId));

        screen.setTheatreOwner(owner);
        return screenRepository.save(screen);
    }
	@Override
    public List<Screen> viewallscreens(int theatreOwnerId) {
        TheatreOwner owner = ownerRepository.findById(theatreOwnerId).get();

       return screenRepository.findByTheatreOwner(owner);
    }
	@Override
    public List<Movie> viewallmovies() 
	{
        return movieRepository.findAll();
    }
	@Override
	public void mapMovieToScreen(ScreenMovieMappingRequest request) {
        Movie movie = movieRepository.findById(request.getMovieid())
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        Screen screen = screenRepository.findById(request.getTheatreid())
                .orElseThrow(() -> new RuntimeException("Screen not found"));

        ScreenMovieMapping mapping = new ScreenMovieMapping();
        mapping.setMovie(movie);
        mapping.setScreen(screen);
        mapping.setShowTime(request.getShowtime());
        mapping.setFromDate(LocalDate.parse(request.getFromdate()));
        mapping.setExpiryDate(LocalDate.parse(request.getExpirydate()));
        mapping.setExecutivePrice(request.getExecutiveprice());
        mapping.setNormalPrice(request.getNormalprice());

        ScreenMovieMapping savedMapping = mappingRepository.save(mapping);


        int executiveRows = screen.getNoofexecutiveseats();
        int normalRows = screen.getNoofnormalseats();
        long days = ChronoUnit.DAYS.between(savedMapping.getFromDate(), savedMapping.getExpiryDate());
        System.out.println("Total days: " + days);

        for (LocalDate d = savedMapping.getFromDate();
        	     !d.isAfter(savedMapping.getExpiryDate());
        	     d = d.plusDays(1)) {

        	    List<Seat> daySeats = new ArrayList<>();

        	    for (int row = 1; row <= executiveRows; row++) {
        	        Seat seat = new Seat();
        	        seat.setSeatType("EXECUTIVE");
        	        seat.setSeatNumber("E" + row);
        	        seat.setStatus(SeatStatus.AVAILABLE);
        	        seat.setScreenMovieMapping(savedMapping);
        	        seat.setDate(d);
        	        daySeats.add(seat);
        	    }

        	    for (int row = 1; row <= normalRows; row++) {
        	        Seat seat = new Seat();
        	        seat.setSeatType("NORMAL");
        	        seat.setSeatNumber("N" + row);
        	        seat.setStatus(SeatStatus.AVAILABLE);
        	        seat.setScreenMovieMapping(savedMapping);
        	        seat.setDate(d);
        	        daySeats.add(seat);
        	    }

        	    seatRepository.saveAll(daySeats); 
        	}

    }

	@Override
	public List<ScreenMovieMapping> viewallmappings(int id) 
	{
		return mappingRepository.findAllByTheatreOwnerId(id);
	}
	
	@Override
	public Double viewvacancy(int id) 
	{
		return mappingRepository.findVacancyPercentage(id);
		
	}
}
