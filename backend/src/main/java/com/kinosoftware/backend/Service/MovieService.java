package com.kinosoftware.backend.Service;

import com.kinosoftware.backend.DTO.AllSeatsForMovieDTO;
import com.kinosoftware.backend.DTO.NewMovieDTO;
import com.kinosoftware.backend.DTO.response.MovieResponse;
import com.kinosoftware.backend.Entity.Hall;
import com.kinosoftware.backend.Entity.Movie;
import com.kinosoftware.backend.Repository.HallRepository;
import com.kinosoftware.backend.Repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final HallRepository hallRepository;

    public MovieService(MovieRepository movieRepository, HallRepository hallRepository) {
        this.movieRepository = movieRepository;
        this.hallRepository = hallRepository;
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
    public Movie insertNewMovie(NewMovieDTO newMovieDTO){
        Hall hall = hallRepository.getById(newMovieDTO.getHall());


        Movie movie = new Movie();
        movie.setTitel(newMovieDTO.getTitel());
        movie.setMovieDate(newMovieDTO.getMovieDate());
        movie.setHall(hall);
        System.out.println(movie.getHall());
        System.out.println(movie.getTitel());
        return movieRepository.save(movie);
    }
}
