package com.kinosoftware.backend.Repository;

import com.kinosoftware.backend.Entity.MovieShowTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieShowTimeRepository extends JpaRepository<MovieShowTime,Long> {
}
