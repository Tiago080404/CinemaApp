package com.kinosoftware.backend.Repository;

import com.kinosoftware.backend.Entity.MovieShowTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

public interface MovieShowTimeRepository extends JpaRepository<MovieShowTime,Long> {
    @Query(value = "select * from movie_showtime where movie_showtime.show_date = ?1",nativeQuery = true)
    List<MovieShowTime> findByShowDate(LocalDate showDate);

    @Query(value = "select * from movie_showtime where movie_showtime.movie_id = ?1",nativeQuery = true)
    List<MovieShowTime> getAllShowsForMovie(Long movieId);
}
