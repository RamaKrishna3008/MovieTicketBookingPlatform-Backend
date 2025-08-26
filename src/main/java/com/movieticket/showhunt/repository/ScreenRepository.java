package com.movieticket.showhunt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movieticket.showhunt.entity.Screen;
import com.movieticket.showhunt.entity.TheatreOwner;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, Integer>
{
	public List<Screen> findByTheatreOwner(TheatreOwner theatreOwner);
}
