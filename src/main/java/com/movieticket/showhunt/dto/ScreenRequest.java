package com.movieticket.showhunt.dto;

public class ScreenRequest {
    private String name;
    private int noOfExecutiveSeats;
    private int noOfNormalSeats;
    private int theatreOwnerId;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getNoOfExecutiveSeats() { return noOfExecutiveSeats; }
    public void setNoOfExecutiveSeats(int noOfExecutiveSeats) { this.noOfExecutiveSeats = noOfExecutiveSeats; }

    public int getNoOfNormalSeats() { return noOfNormalSeats; }
    public void setNoOfNormalSeats(int noOfNormalSeats) { this.noOfNormalSeats = noOfNormalSeats; }

    public int getTheatreOwnerId() { return theatreOwnerId; }
    public void setTheatreOwnerId(int theatreOwnerId) { this.theatreOwnerId = theatreOwnerId; }
}
