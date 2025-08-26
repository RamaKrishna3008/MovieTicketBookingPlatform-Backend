package com.movieticket.showhunt.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class ScreenMovieMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "executiveprice", nullable = false)
    private double executivePrice;

    @Column(name = "normalprices", nullable = false)
    private double normalPrice;

    @Column(name = "from_date", nullable = false)
    private LocalDate fromDate;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @Column(name = "show_time", nullable = false, length = 100)
    private String showTime;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    @JsonIgnore
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "screen_id", nullable = false)
    @JsonIgnore
    private Screen screen;

    @OneToMany(mappedBy = "screenMovieMapping", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Seat> seats = new ArrayList<>();


    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getExecutivePrice() {
        return executivePrice;
    }

    public void setExecutivePrice(double executivePrice) {
        this.executivePrice = executivePrice;
    }

    public double getNormalPrice() {
        return normalPrice;
    }

    public void setNormalPrice(double normalPrice) {
        this.normalPrice = normalPrice;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
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

	@Override
	public String toString() {
		return "ScreenMovieMapping [id=" + id + ", executivePrice=" + executivePrice + ", normalPrice=" + normalPrice
				+ ", fromDate=" + fromDate + ", expiryDate=" + expiryDate + ", showTime=" + showTime + ", movie="
				+ movie + ", screen=" + screen + ", seats=" + seats + "]";
	}
    
}
