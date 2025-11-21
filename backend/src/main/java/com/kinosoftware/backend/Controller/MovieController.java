package com.kinosoftware.backend.Controller;

import com.kinosoftware.backend.DTO.AllSeatsForMovieDTO;
import com.kinosoftware.backend.DTO.NewMovieDTO;
import com.kinosoftware.backend.DTO.response.MovieResponse;
import com.kinosoftware.backend.Entity.Movie;
import com.kinosoftware.backend.Entity.MovieShowTime;
import com.kinosoftware.backend.Service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalDate;
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
    @GetMapping("/seats/{showTimeId}")
    public List<AllSeatsForMovieDTO> getAllSeatsByMovie(@PathVariable Long showTimeId){
        return movieService.getAllSeatsForMovie(showTimeId);
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

    @GetMapping("/all/{movieId}")
    public ResponseEntity<?> getAllShowsForMovie(@PathVariable Long movieId){
        List<MovieShowTime> showTimesForMovie= movieService.getAllShowsForMovie(movieId);
        Map<String,Object> response = new HashMap<>();
        if(showTimesForMovie==null){
            response.put("message","no shows fo this movie");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }else{
            response.put("message","Got all shows for current movie");
            response.put("showTimes",showTimesForMovie);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }
}
