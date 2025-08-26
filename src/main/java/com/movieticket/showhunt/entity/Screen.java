package com.movieticket.showhunt.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "screen_table")
public class Screen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "noofexecutiveseats", nullable = false)
    private int noofexecutiveseats;

    @Column(name = "noofnormalseats", nullable = false)
    private int noofnormalseats;

    @ManyToOne
    @JoinColumn(name = "theatre_owner_id")
    private TheatreOwner theatreOwner;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public int getNoofexecutiveseats() {
		return noofexecutiveseats;
	}

	public void setNoofexecutiveseats(int noofexecutiveseats) {
		this.noofexecutiveseats = noofexecutiveseats;
	}

	public int getNoofnormalseats() {
		return noofnormalseats;
	}

	public void setNoofnormalseats(int noofnormalseats) {
		this.noofnormalseats = noofnormalseats;
	}

	public TheatreOwner getTheatreOwner() {
        return theatreOwner;
    }

    public void setTheatreOwner(TheatreOwner theatreOwner) {
        this.theatreOwner = theatreOwner;
    }
}
