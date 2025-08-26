package com.movieticket.showhunt.service;

import java.util.List;

import com.movieticket.showhunt.dto.ScreenMovieMappingRequest;
import com.movieticket.showhunt.entity.Movie;
import com.movieticket.showhunt.entity.Screen;
import com.movieticket.showhunt.entity.ScreenMovieMapping;
import com.movieticket.showhunt.entity.TheatreOwner;

public interface TheatreOwnerService 
{
	public TheatreOwner checkLogin(String username, String password);
	public Screen addScreen(int theatreOwnerId, Screen screen);
	public List<Screen> viewallscreens(int theatreOwnerId);
	public List<Movie> viewallmovies();
	public void mapMovieToScreen(ScreenMovieMappingRequest request);
	public List<ScreenMovieMapping> viewallmappings(int id);
	public Double viewvacancy(int id);
}
