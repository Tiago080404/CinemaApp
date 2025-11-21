package com.kinosoftware.backend;

import com.kinosoftware.backend.DTO.NewMovieApiDTO;
import com.kinosoftware.backend.Entity.Hall;
import com.kinosoftware.backend.Entity.Movie;
import com.kinosoftware.backend.Entity.MovieShowTime;
import com.kinosoftware.backend.Repository.HallRepository;
import com.kinosoftware.backend.Repository.MovieRepository;
import com.kinosoftware.backend.Repository.MovieShowTimeRepository;
import com.kinosoftware.backend.Service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class MovieServiceTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieShowTimeRepository movieShowTimeRepository;

//    @Test
//    void getMovie(){
//        Optional<Movie> movie = movieRepository.findById(9L);
//        Assertions.assertEquals("Demon Slayer",movie.get().getTitel());
//    }
//    @Test
//    void addNewMovie(){
//        Movie movie = new Movie();
//        movie.setMovieDate(LocalDateTime.now().plusDays(2));
//        movie.setMovieId(7L);
//        movie.setTitel("Testmovie");
//        Hall hall = hallRepository.findById(1L)
//                .orElseThrow(() -> new RuntimeException("Hall not found"));;
//        movie.setHall(hall);
//
//        movieRepository.save(movie);
//
//        Movie insertedMovie = movieRepository.findById(9L)
//                .orElseThrow(()->new RuntimeException("Movie not found"));
//
//        assertEquals(insertedMovie.getMovieId(),movie.getMovieId());
//    }

    @Test
    @Sql(scripts = "/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(scripts = "/movieInsert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void insertNewMovieApi() {
        NewMovieApiDTO newMovieApiDTO = new NewMovieApiDTO();
        newMovieApiDTO.setImage("/22dj38IckjzEEUZwN1tPU5VJ1qq.jpg");
        newMovieApiDTO.setMovieDate(LocalDate.parse("2025-12-01"));
        newMovieApiDTO.setTitel("tester");
        movieService.insertMoviesFromApi(newMovieApiDTO);


        List<MovieShowTime> movieShowTimes = movieShowTimeRepository.findAll();


        log.info("Hall of new inserted showTime{}", movieShowTimes.getLast().getHall().getHallId());
        log.info("Hall of first element{}", movieShowTimes.getFirst().getHall().getHallId());
        log.info("Time of inserted showTime{}", movieShowTimes.getLast().getShow_time());
        assertNotSame(movieShowTimes.getFirst(), movieShowTimes.getLast());
    }

    @Test
    @Sql(scripts = "/clean.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void insertWhenMovieShowTimeEmpty() {


        NewMovieApiDTO newMovieApiDTO = new NewMovieApiDTO();
        newMovieApiDTO.setImage("/22dj38IckjzEEUZwN1tPU5VJ1qq.jpg");
        newMovieApiDTO.setMovieDate(LocalDate.parse("2025-12-01"));
        newMovieApiDTO.setTitel("tester");
        movieService.insertMoviesFromApi(newMovieApiDTO);

        List<MovieShowTime> movieShowTimes = movieShowTimeRepository.findAll();
        log.info("ALL hallls{}", movieShowTimes);

        log.info("Hall of new inserted showTime{}", movieShowTimes.getLast().getHall().getHallId());
        log.info("Hall of first element{}", movieShowTimes.getFirst().getHall().getHallId());

        log.info("time of new inserted{}", movieShowTimes.getLast().getShow_time());
        log.info("time of first{}", movieShowTimes.getFirst().getShow_time());
        assertEquals(1, movieShowTimes.size());
    }

    @Test
    @Sql(scripts = "/movieInsert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void insertWhenMovieShowTimeHasDifferentDayInDB() {
        NewMovieApiDTO newMovieApiDTO = new NewMovieApiDTO();
        newMovieApiDTO.setImage("/22dj38IckjzEEUZwN1tPU5VJ1qq.jpg");
        newMovieApiDTO.setMovieDate(LocalDate.parse("2025-12-02"));
        newMovieApiDTO.setTitel("othermovie");
        movieService.insertMoviesFromApi(newMovieApiDTO);

        List<MovieShowTime> movieShowTimes = movieShowTimeRepository.findAll();

        assertEquals(1L, movieShowTimes.getLast().getHall().getHallId());
    }
}
