package com.movieticket.showhunt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movieticket.showhunt.entity.TheatreOwner;

@Repository
public interface TheatreOwnerRepository extends JpaRepository<TheatreOwner, Integer>
{
	public TheatreOwner findByUsernameAndPassword(String username, String password);
}
