package com.movieticket.showhunt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movieticket.showhunt.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer>
{

}
