package com.kinosoftware.backend.Controller;

import com.kinosoftware.backend.DTO.AllSeatsForMovieDTO;
import com.kinosoftware.backend.DTO.NewMovieDTO;
import com.kinosoftware.backend.DTO.response.MovieResponse;
import com.kinosoftware.backend.Entity.Movie;
import com.kinosoftware.backend.Service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @PostMapping("/insert")
    public ResponseEntity<?> setNewMovie(@RequestBody NewMovieDTO newMovieDTO){
        Movie newMovie = movieService.insertNewMovie(newMovieDTO);
        System.out.println(newMovie.getMovieId());
        Map<String,Object> response = new HashMap<>();
        response.put("message", "New Movie added");
        response.put("movie",newMovieDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/getnew/movies")
    public void getNewMovies(){
        movieService.getMoviesForWeek();
    }
}
