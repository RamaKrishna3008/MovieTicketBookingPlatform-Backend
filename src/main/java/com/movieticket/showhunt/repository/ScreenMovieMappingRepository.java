package com.movieticket.showhunt.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.movieticket.showhunt.entity.Movie;
import com.movieticket.showhunt.entity.ScreenMovieMapping;

@Repository
public interface ScreenMovieMappingRepository extends JpaRepository<ScreenMovieMapping, Integer> 
{
	@Query("SELECT smm FROM ScreenMovieMapping smm WHERE smm.screen.theatreOwner.id = :ownerId")
	List<ScreenMovieMapping> findAllByTheatreOwnerId(@Param("ownerId") int ownerId);
	
	@Query("""
	        SELECT (COUNT(s) * 100.0) / 
	               (SELECT (sc.noofexecutiveseats + sc.noofnormalseats) 
	                FROM Screen sc 
	                WHERE sc.id = (SELECT smm.screen.id 
	                               FROM ScreenMovieMapping smm 
	                               WHERE smm.id = :mappingId))
	        FROM Seat s
	        WHERE s.status = 'available' 
	        AND s.screenMovieMapping.id = :mappingId
	    """)
	    Double findVacancyPercentage(@Param("mappingId") int mappingId);
		public List<ScreenMovieMapping> findByMovie(Movie m);
		@Query("SELECT DISTINCT smm.movie FROM ScreenMovieMapping smm WHERE smm.expiryDate >= :today")
		List<Movie> findDistinctMoviesWithActiveMappings(@Param("today") LocalDate today);
		@Query("""
			    SELECT smm 
			    FROM ScreenMovieMapping smm 
			    WHERE smm.movie.id = :movieId 
			      AND smm.expiryDate >= :today
			    ORDER BY smm.fromDate, smm.showTime
			""")
			List<ScreenMovieMapping> findActiveMappingsByMovieId(
			        @Param("movieId") int movieId,
			        @Param("today") LocalDate today);
		
		@Query("""
			       SELECT DISTINCT smm.movie 
			       FROM ScreenMovieMapping smm
			       JOIN smm.screen s
			       JOIN s.theatreOwner t
			       WHERE smm.expiryDate >= :today
			         AND (:city IS NULL OR LOWER(t.city) = LOWER(:city))
			       """)
			List<Movie> findDistinctMoviesWithCity(
			        @Param("today") LocalDate today,
			        @Param("city") String city);
		
	
}

