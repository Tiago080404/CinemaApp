package com.kinosoftware.backend.Repository;

import com.kinosoftware.backend.DTO.AllSeatsForMovieDTO;
import com.kinosoftware.backend.Entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    //@Query(value = "select m.movie_id,mss.status as status,s.* from movie m join hall h on m.hall =h.id left join seats s on h.id =s.hall_id left join movie_seat_status mss on s.seats_id = mss.seat_id and m.movie_id =mss.movie_id where m.movie_id =?1",nativeQuery = true)
//    @Query(value = "select m.movie_id ,mss.status as status,s.* from movie m join movie_showtime ms on m.movie_id =ms.movie_id join hall h on ms.hall = h.id join seats s on h.id =s.hall_id left join movie_seat_status mss on s.seats_id =mss.seat_id and m.movie_id =mss.movie_id where m.movie_id =?1 ", nativeQuery = true)
//    List<AllSeatsForMovieDTO> getAllSeatsFromMovie(Long id);

    @Query(value = "select m.movie_id ,mss.status as status,s.* from movie m join movie_showtime ms on m.movie_id =ms.movie_id join hall h on ms.hall = h.id join seats s on h.id =s.hall_id left join movie_seat_status mss on s.seats_id =mss.seat_id and m.movie_id =mss.movie_id where m.movie_id = ?1 and ms.show_date = ?2 and ms.show_time = ?3",nativeQuery = true)
    List<AllSeatsForMovieDTO> getAllSeatsFromMovie(Long id, LocalDate date, Time time);

    List<Movie> findByTitel(String titel);
}
