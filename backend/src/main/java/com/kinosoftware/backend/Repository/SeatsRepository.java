package com.kinosoftware.backend.Repository;

import com.kinosoftware.backend.Entity.Seats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatsRepository extends JpaRepository<Seats,Long> {
}
