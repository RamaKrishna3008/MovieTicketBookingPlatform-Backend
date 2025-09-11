package com.movieticket.showhunt.dto;


public class ScreenMovieMappingRequest {
    private int movieid;
    private int theatreid; 
    private String showtime;
    private String fromdate; 
    private String expirydate;
    private double executiveprice;
    private double normalprice;
    
	public int getMovieid() {
		return movieid;
	}
	public void setMovieid(int movieid) {
		this.movieid = movieid;
	}
	public int getTheatreid() {
		return theatreid;
	}
	public void setTheatreid(int theatreid) {
		this.theatreid = theatreid;
	}
	public String getShowtime() {
		return showtime;
	}
	public void setShowtime(String showtime) {
		this.showtime = showtime;
	}
	public String getFromdate() {
		return fromdate;
	}
	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}
	public String getExpirydate() {
		return expirydate;
	}
	public void setExpirydate(String expirydate) {
		this.expirydate = expirydate;
	}
	public double getExecutiveprice() {
		return executiveprice;
	}
	public void setExecutiveprice(double executiveprice) {
		this.executiveprice = executiveprice;
	}
	public double getNormalprice() {
		return normalprice;
	}
	public void setNormalprice(double normalprice) {
		this.normalprice = normalprice;
	}
}
