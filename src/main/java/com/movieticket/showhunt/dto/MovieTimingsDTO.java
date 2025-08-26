package com.movieticket.showhunt.dto;

import java.util.List;

import com.movieticket.showhunt.entity.Movie;

public class MovieTimingsDTO {
    private Movie movieData;
    private List<DateTimingsDTO> timings;

    public MovieTimingsDTO(Movie movieData, List<DateTimingsDTO> timings) {
        this.movieData = movieData;
        this.timings = timings;
    }

	public Movie getMovieData() {
		return movieData;
	}

	public void setMovieData(Movie movieData) {
		this.movieData = movieData;
	}

	public List<DateTimingsDTO> getTimings() {
		return timings;
	}

	public void setTimings(List<DateTimingsDTO> timings) {
		this.timings = timings;
	}
}
