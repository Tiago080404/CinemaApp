package com.kinosoftware.backend.Repository;

import com.kinosoftware.backend.DTO.AllSeatsForMovieDTO;
import com.kinosoftware.backend.Entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie,Long> {

    @Query(value = "select m.movie_id,mss.status as status,s.* from movie m join hall h on m.hall =h.id left join seats s on h.id =s.hall_id left join movie_seat_status mss on s.seats_id = mss.seat_id where m.movie_id =?1",nativeQuery = true)
    List<AllSeatsForMovieDTO> getAllSeatsFromMovie(Long id);
}
