package com.kinosoftware.backend;

import com.kinosoftware.backend.Entity.Movie;
import com.kinosoftware.backend.Repository.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class MovieServiceTest {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    void getMovie(){
        Optional<Movie> movie = movieRepository.findById(1L);
        Assertions.assertEquals("Demon Slayer",movie.get().getTitel());

    }
}
