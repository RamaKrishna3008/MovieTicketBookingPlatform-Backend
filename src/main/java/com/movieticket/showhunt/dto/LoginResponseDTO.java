package com.movieticket.showhunt.dto;

import com.movieticket.showhunt.entity.Admin;
import com.movieticket.showhunt.entity.TheatreOwner;
import com.movieticket.showhunt.entity.User;

public class LoginResponseDTO {

    private String role;
    private Object userDetails;

    public LoginResponseDTO(String role, Admin admin) {
        this.role = role;
        this.userDetails = admin;
    }

    public LoginResponseDTO(String role, TheatreOwner theatreOwner) {
        this.role = role;
        this.userDetails = theatreOwner;
    }

    public LoginResponseDTO(String role, User user) {
        this.role = role;
        this.userDetails = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

	public Object getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(Object userDetails) {
		this.userDetails = userDetails;
	}

    
}
