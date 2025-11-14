package com.kinosoftware.backend.Repository;

import com.kinosoftware.backend.DTO.GetMovieSeatsResponseDTO;
import com.kinosoftware.backend.Entity.Movie;
import com.kinosoftware.backend.Entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    @Query(value = "select movie.titel,seats.row_num,seats.seat_num from movie join hall on hall.id = movie.hall join seats on seats.hall_id = hall.id where movie.movie_id = ?1",nativeQuery = true)
    List<GetMovieSeatsResponseDTO> getMovieSeats(Long movieId);

    @Query(value = "select * from reservations where reservations.reservation_time::date = CURRENT_DATE",nativeQuery = true)
    List<Reservation> reservationToday();
}
