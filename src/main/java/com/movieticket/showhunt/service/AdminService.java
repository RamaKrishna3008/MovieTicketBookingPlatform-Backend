package com.movieticket.showhunt.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.movieticket.showhunt.entity.Admin;
import com.movieticket.showhunt.entity.Movie;
import com.movieticket.showhunt.entity.TheatreOwner;
import com.movieticket.showhunt.entity.User;

public interface AdminService 
{
	public Admin checkAdminLogin(String username, String password);
	public List<User> viewallusers();
	public List<TheatreOwner> viewalltheatreowners();
	public TheatreOwner gettheatreowner(int id);
	public List<Movie> viewallmovies();
	public String addtheatreowner(TheatreOwner owner);
	public Movie addMovie(Movie movie, MultipartFile posterFile) throws IOException;
	public String updatetheatreownerstatus(int id);
	public String updatetheatreowner(TheatreOwner to);
}
