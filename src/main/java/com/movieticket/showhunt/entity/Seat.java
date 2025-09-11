package com.movieticket.showhunt.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "seat_number")
    private String seatNumber;

    @Column(name = "seat_type")
    private String seatType; 

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "blocked_at")
    private LocalDateTime blockedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SeatStatus status;

    @ManyToOne
    @JoinColumn(name = "smm_id")
    private ScreenMovieMapping screenMovieMapping;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDateTime getBlockedAt() {
        return blockedAt;
    }

    public void setBlockedAt(LocalDateTime blockedAt) {
        this.blockedAt = blockedAt;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public void setStatus(SeatStatus status) {
        this.status = status;
    }

    public ScreenMovieMapping getScreenMovieMapping() {
        return screenMovieMapping;
    }

    public void setScreenMovieMapping(ScreenMovieMapping screenMovieMapping) {
        this.screenMovieMapping = screenMovieMapping;
    }
}
