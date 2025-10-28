package com.kinosoftware.backend.Repository;

import com.kinosoftware.backend.Entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie,Long> {
}
