package com.kinosoftware.backend.Repository;

import com.kinosoftware.backend.DTO.AllMoviesWithDateDTO;
import com.kinosoftware.backend.DTO.AllSeatsForMovieDTO;
import com.kinosoftware.backend.Entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query(value = "select msss.status as status,s.* from seats s left join movie_showtime_seat_status msss on s.seats_id =msss.seat_id and msss.showtime_id =?1",nativeQuery = true)
    List<AllSeatsForMovieDTO> getAllSeatsFromMovie(Long movieShowTimeId);

    @Query(value = "select movie.*,movie_showtime.show_date from movie join movie_showtime on movie.movie_id=movie_showtime.movie_id",nativeQuery = true)
    List<AllMoviesWithDateDTO> getAllMoviesWithDate();

    List<Movie> findByTitel(String titel);

    List<Movie> getByTitel(String titel);
}
