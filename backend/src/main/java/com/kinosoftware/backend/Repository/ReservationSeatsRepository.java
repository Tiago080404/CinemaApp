package com.kinosoftware.backend.Repository;

import com.kinosoftware.backend.Entity.ReservationSeats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationSeatsRepository extends JpaRepository<ReservationSeats,Long> {
}
