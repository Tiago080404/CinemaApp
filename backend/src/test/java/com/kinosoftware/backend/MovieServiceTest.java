package com.kinosoftware.backend;

import com.kinosoftware.backend.Entity.Hall;
import com.kinosoftware.backend.Entity.Movie;
import com.kinosoftware.backend.Repository.HallRepository;
import com.kinosoftware.backend.Repository.MovieRepository;
import com.kinosoftware.backend.Service.MovieService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class MovieServiceTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private MovieService movieService;

    @Test
    void getMovie(){
        Optional<Movie> movie = movieRepository.findById(9L);
        Assertions.assertEquals("Demon Slayer",movie.get().getTitel());
    }
    @Test
    void addNewMovie(){
        Movie movie = new Movie();
        movie.setMovieDate(LocalDateTime.now().plusDays(2));
        movie.setMovieId(7L);
        movie.setTitel("Testmovie");
        Hall hall = hallRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Hall not found"));;
        movie.setHall(hall);

        movieRepository.save(movie);

        Movie insertedMovie = movieRepository.findById(9L)
                .orElseThrow(()->new RuntimeException("Movie not found"));

        assertEquals(insertedMovie.getMovieId(),movie.getMovieId());
    }
}
