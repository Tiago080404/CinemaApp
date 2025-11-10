package com.kinosoftware.backend.Service;

import com.kinosoftware.backend.DTO.AllSeatsForMovieDTO;
import com.kinosoftware.backend.DTO.response.MovieResponse;
import com.kinosoftware.backend.Entity.Movie;
import com.kinosoftware.backend.Repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies(){
        return movieRepository.findAll();
    }

    public Optional<MovieResponse> getMovieById(Long id){
        return movieRepository.findById(id).map(movie -> new MovieResponse(
                movie.getMovieId(),
                movie.getTitel(),
                movie.getMovieDate(),
                movie.getHall()
        ));
    }

    public List<AllSeatsForMovieDTO> getAllSeatsForMovie(Long id){
        return movieRepository.getAllSeatsFromMovie(id);
    }
}
