package com.kinosoftware.backend.Repository;

import com.kinosoftware.backend.Entity.MovieSeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieSeatsStatusRepository extends JpaRepository<MovieSeatStatus,Long> {
}
