package com.kinosoftware.backend.Repository;

import com.kinosoftware.backend.Entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {

}
