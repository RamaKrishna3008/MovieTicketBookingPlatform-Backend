package com.movieticket.showhunt.dto;

import java.util.List;

import com.movieticket.showhunt.entity.Movie;
import com.movieticket.showhunt.entity.Screen;
import com.movieticket.showhunt.entity.ScreenMovieMapping;
import com.movieticket.showhunt.entity.Seat;

public class ScreenMovieMappingResponseDTO {
    private List<Seat> seats;
    private Movie movie;
    private Screen screen; 
    private double normalprices;
    private double executiveprice;

    public ScreenMovieMappingResponseDTO(ScreenMovieMapping mapping, List<Seat> seats) {
        this.seats = seats;
        this.movie = mapping.getMovie();
        this.screen = mapping.getScreen(); 
        this.normalprices = mapping.getNormalPrice();
        this.executiveprice = mapping.getExecutivePrice();
    }

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Screen getScreen() {
		return screen;
	}

	public void setScreen(Screen screen) {
		this.screen = screen;
	}

	public double getNormalprices() {
		return normalprices;
	}

	public void setNormalprices(double normalprices) {
		this.normalprices = normalprices;
	}

	public double getExecutiveprice() {
		return executiveprice;
	}

	public void setExecutiveprice(double executiveprice) {
		this.executiveprice = executiveprice;
	}
}
