package com.kinosoftware.backend.Controller;

import com.kinosoftware.backend.DTO.AllSeatsForMovieDTO;
import com.kinosoftware.backend.DTO.response.MovieResponse;
import com.kinosoftware.backend.Entity.Movie;
import com.kinosoftware.backend.Service.MovieService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public List<Movie> getAllMovies(){
        return movieService.getAllMovies();
    }

    @GetMapping("/{movieId}")
    public Optional<MovieResponse> getMovieById(@PathVariable Long movieId){
        System.out.println(movieId);
        return movieService.getMovieById(movieId);
    }
    @GetMapping("/seats/{movieId}")
    public List<AllSeatsForMovieDTO> getAllSeatsByMovie(@PathVariable Long movieId){
        return movieService.getAllSeatsForMovie(movieId);
    }
}
