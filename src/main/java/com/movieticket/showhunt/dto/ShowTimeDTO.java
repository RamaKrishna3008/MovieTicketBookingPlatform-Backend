package com.movieticket.showhunt.dto;

import com.movieticket.showhunt.entity.ScreenMovieMapping;

public class ShowTimeDTO {
	private int id;
    private String time;
    private String theatreName;
    private long occupancy; 
    public ShowTimeDTO(int id, String time, String theatreName, long occupancy) {
        this.id = id;
        this.time = time;
        this.theatreName = theatreName;
        this.occupancy = occupancy;
    }
    public ShowTimeDTO(ScreenMovieMapping mapping, int occupancy) {
        this.id = mapping.getId();
        this.time = mapping.getShowTime();
        this.theatreName = mapping.getScreen().getTheatreOwner().getTheatrename();
        this.occupancy = occupancy;
    }

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTheatreName() {
		return theatreName;
	}
	public void setTheatreName(String theatreName) {
		this.theatreName = theatreName;
	}
	public long getOccupancy() {
		return occupancy;
	}
	public void setOccupancy(long occupancy) {
		this.occupancy = occupancy;
	}
}