package com.movieticket.showhunt.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.movieticket.showhunt.entity.Movie;
import com.movieticket.showhunt.entity.TheatreOwner;
import com.movieticket.showhunt.service.AdminService;


@RestController
@RequestMapping("admin")
public class AdminController
{
	@Autowired
	private AdminService service;
	
	@GetMapping("viewallusers")
	public ResponseEntity<?> viewallusers()
	{
		try
		{
			return ResponseEntity.ok(service.viewallusers());
		}
		catch (Exception e) 
		{
			return ResponseEntity.status(500).body("Failed To fetch");
		}
	}
	@GetMapping("viewalltheatreowners")
	public ResponseEntity<?> viewalltheatreowners()
	{
		try
		{
			return ResponseEntity.ok(service.viewalltheatreowners());
		}
		catch (Exception e) 
		{
			return ResponseEntity.status(500).body("Failed To fetch");
		}
	}
	@GetMapping("gettheatreowner/{id}")
	public ResponseEntity<?> gettheatreowner(@PathVariable int id)
	{
		try
		{
			return ResponseEntity.ok(service.gettheatreowner(id));
		}
		catch (Exception e) 
		{
			return ResponseEntity.status(500).body("Failed To fetch");
		}
	}
	@GetMapping("viewallmovies")
	public ResponseEntity<?> viewallmovies()
	{
		try
		{
			return ResponseEntity.ok(service.viewallmovies());
		}
		catch (Exception e) 
		{
			return ResponseEntity.status(500).body("Failed To fetch");
		}
	}
	@PostMapping("addtheatreowner")
	public ResponseEntity<String> addtheatreowner(@RequestBody TheatreOwner owner) 
	{
		try
		{
			return ResponseEntity.status(201).body(service.addtheatreowner(owner));
		}
		catch (Exception e) 
		{
			return ResponseEntity.status(500).body("Failed To Add");
		}
	}
	 @PostMapping("/addmovie")
	    public ResponseEntity<Movie> addMovie(
	            @RequestPart("movie") Movie movie,
	            @RequestPart("poster") MultipartFile posterFile) {
	        try {
	            Movie savedMovie = service.addMovie(movie, posterFile);
	            return new ResponseEntity<>(savedMovie, HttpStatus.CREATED);
	        } catch (IOException e) {
	            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	 
	 @PutMapping("updatetheatreownerstatus/{id}")
	 public ResponseEntity<String> updatetheatreownerstatus(@PathVariable int id)
	 {
		 try
			{
				return ResponseEntity.status(200).body(service.updatetheatreownerstatus(id));
			}
			catch (Exception e) 
			{
				return ResponseEntity.status(500).body("Failed To Update");
			}
	 }
	 @PutMapping("updatetheatreowner")
	 public ResponseEntity<String> updatetheatreowner(@RequestBody TheatreOwner owner)
	 {
		 try
			{
				return ResponseEntity.status(200).body(service.updatetheatreowner(owner));
			}
			catch (Exception e) 
			{
				return ResponseEntity.status(500).body("Failed To Update");
			}
	 }
}
