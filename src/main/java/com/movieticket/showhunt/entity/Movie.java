package com.movieticket.showhunt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "movie_table")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 255)
    private String cast;

    @Column(nullable = false, length = 255)
    private String description;

    @Column(nullable = false, length = 30)
    private String genre;

    @Column(nullable = false)
    private int duration;

    @Column(nullable = false, length = 50)
    private String releasedate;

    @Column(nullable = false, length = 255)
    private String movieposter;

    @Column(nullable = false, length = 30)
    private String language;
    
    @Column(nullable = false)
    private String trailerUrl;

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

    public String getCast() {
        return cast;
    }
    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getReleasedate() {
        return releasedate;
    }
    public void setReleasedate(String releasedate) {
        this.releasedate = releasedate;
    }

    public String getMovieposter() {
        return movieposter;
    }
    public void setMovieposter(String movieposter) {
        this.movieposter = movieposter;
    }

    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
	public String getTrailerUrl() {
		return trailerUrl;
	}
	public void setTrailerUrl(String trailerUrl) {
		this.trailerUrl = trailerUrl;
	}
}
