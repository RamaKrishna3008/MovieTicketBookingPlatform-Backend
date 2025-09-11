package com.movieticket.showhunt.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.movieticket.showhunt.entity.Seat;

public interface SeatRepository extends JpaRepository<Seat, Integer> 
{
	@Query("""
		    SELECT 
		        (CAST(COUNT(s) AS double) / 
		        (SELECT COUNT(s2) 
		         FROM Seat s2 
		         WHERE s2.screenMovieMapping.id = :mappingId
		           AND s2.date = :date))
		    FROM Seat s
		    WHERE s.screenMovieMapping.id = :mappingId
		      AND s.date = :date
		      AND s.status = 'AVAILABLE'
		""")
		Double findAvailableSeatsCountForDate(@Param("mappingId") int mappingId,
		                            @Param("date") LocalDate date);
	@Query("""
	        SELECT s
	        FROM Seat s
	        WHERE s.screenMovieMapping.id = :mappingId
	          AND s.date = :date
	          AND s.screenMovieMapping.showTime = :showTime
	    """)
	    List<Seat> findByMappingAndDateAndShowTime(
	            @Param("mappingId") int mappingId,
	            @Param("date") LocalDate date,
	            @Param("showTime") String showTime);
	 @Query("""
		        SELECT s FROM Seat s 
		        WHERE s.screenMovieMapping.id = :mappingId
		        AND s.date = :date
		        AND s.seatNumber IN :seatNumbers
		    """)
		    List<Seat> findByMappingIdAndDateAndSeatNumberIn(@Param("mappingId") int mappingId,
		                                                     @Param("date") LocalDate date,
		                                                     @Param("seatNumbers") List<String> seatNumbers);
}
