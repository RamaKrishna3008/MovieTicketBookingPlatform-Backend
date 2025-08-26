package com.movieticket.showhunt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.movieticket.showhunt.dto.ScreenMovieMappingRequest;
import com.movieticket.showhunt.dto.ScreenRequest;
import com.movieticket.showhunt.entity.Screen;
import com.movieticket.showhunt.service.TheatreOwnerService;

@RestController
@RequestMapping("theatreowner")
public class TheatreOwnerController 
{
	@Autowired
	private TheatreOwnerService service;
	 @PostMapping("/addscreen")
	    public ResponseEntity<Screen> addScreen(@RequestBody ScreenRequest request) {
	        Screen screen = new Screen();
	        screen.setName(request.getName());
	        screen.setNoofexecutiveseats(request.getNoOfExecutiveSeats());
	        screen.setNoofnormalseats(request.getNoOfNormalSeats());

	        Screen savedScreen = service.addScreen(request.getTheatreOwnerId(), screen);
	        return new ResponseEntity<>(savedScreen, HttpStatus.CREATED);
	    }
	 
	@GetMapping("viewallscreens/{id}")
	public ResponseEntity<?> viewallscreens(@PathVariable int id)
	{
		try {
			return ResponseEntity.ok(service.viewallscreens(id));
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Failed To Fetch");
		}
	}
	@GetMapping("viewallmovies")
	public ResponseEntity<?> viewallmovies()
	{
		try {
			return ResponseEntity.ok(service.viewallmovies());
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Failed To Fetch");
		}
	}
	 @PostMapping("/mapmovietoscreen")
	    public ResponseEntity<String> mapMovieToScreen(@RequestBody ScreenMovieMappingRequest request) {
	        try {
	            service.mapMovieToScreen(request);
	            return ResponseEntity.ok("Movie mapped to screen successfully.");
	        } catch (RuntimeException e) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to map movie: " + e.getMessage());
	        }
	    }
	 @GetMapping("viewallmappings")
		public ResponseEntity<?> viewallmappings(@RequestParam int id)
		{
			try {
				return ResponseEntity.ok(service.viewallmappings(id));
			} catch (Exception e) {
				return ResponseEntity.status(500).body("Failed To Fetch");
			}
		}
	 @GetMapping("viewvacancy")
		public ResponseEntity<?> viewvacancy(@RequestParam int id)
		{
			try {
				return ResponseEntity.ok(service.viewvacancy(id));
			} catch (Exception e) {
				return ResponseEntity.status(500).body("Failed To Fetch");
			}
		}
}
