package com.movieticket.showhunt.dto;

import java.time.LocalDate;
import java.util.List;

public class DateTimingsDTO {
    private LocalDate date;
    private List<ShowTimeDTO> times;

    public DateTimingsDTO(LocalDate date, List<ShowTimeDTO> times) {
        this.date = date;
        this.times = times;
    }

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public List<ShowTimeDTO> getTimes() {
		return times;
	}

	public void setTimes(List<ShowTimeDTO> times) {
		this.times = times;
	}

	}
