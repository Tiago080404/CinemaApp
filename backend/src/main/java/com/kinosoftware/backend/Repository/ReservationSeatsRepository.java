package com.kinosoftware.backend.Repository;

import com.kinosoftware.backend.Entity.ReservationSeats;
import com.kinosoftware.backend.Entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ReservationSeatsRepository extends JpaRepository<ReservationSeats,Long> {
    @Query(value = "select mss.status from movie join hall on movie.hall = hall.id join seats on hall.id = seats.hall_id join movie_seat_status mss on seats.seats_id = mss.seat_id where movie.movie_id = ?1 and seats.row_num = ?2 and seats.seat_num =?3",nativeQuery = true)
    Status checkAvaialbleSeats(Long id, int rowNum, int seatNum);
}