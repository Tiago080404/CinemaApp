package com.kinosoftware.backend.Service;

import com.kinosoftware.backend.DTO.AllSeatsForMovieDTO;
import com.kinosoftware.backend.DTO.NewMovieApiDTO;
import com.kinosoftware.backend.DTO.NewMovieDTO;
import com.kinosoftware.backend.DTO.response.MovieApiResponse;
import com.kinosoftware.backend.DTO.response.MovieResponse;
import com.kinosoftware.backend.DTO.response.ResultMovie;
import com.kinosoftware.backend.Entity.Hall;
import com.kinosoftware.backend.Entity.Movie;
import com.kinosoftware.backend.Entity.MovieShowTime;
import com.kinosoftware.backend.Repository.HallRepository;
import com.kinosoftware.backend.Repository.MovieRepository;
import com.kinosoftware.backend.Repository.MovieShowTimeRepository;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Time;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final HallRepository hallRepository;
    private final MovieShowTimeRepository movieShowTimeRepository;

    public MovieService(MovieRepository movieRepository, HallRepository hallRepository, MovieShowTimeRepository movieShowTimeRepository) {
        this.movieRepository = movieRepository;
        this.hallRepository = hallRepository;
        this.movieShowTimeRepository = movieShowTimeRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Optional<MovieResponse> getMovieById(Long id) {
        return movieRepository.findById(id).map(movie -> new MovieResponse(
                movie.getMovieId(),
                movie.getTitel()
                //movie.getMovieDate()
                //movie.getHall()
        ));
    }

    public List<AllSeatsForMovieDTO> getAllSeatsForMovie(Long id) {
        return movieRepository.getAllSeatsFromMovie(id);
    }

    public Movie insertNewMovie(NewMovieDTO newMovieDTO) {
        Hall hall = hallRepository.getById(newMovieDTO.getHall());


        Movie movie = new Movie();
        movie.setTitel(newMovieDTO.getTitel());
        //movie.setMovieDate(newMovieDTO.getMovieDate());
        // movie.setHall(hall);
        //System.out.println(movie.getHall());
        System.out.println(movie.getTitel());
        return movieRepository.save(movie);
    }

    public void getMoviesForWeek() {
        RestTemplate restTemplate = new RestTemplate();
        String apiKey = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlMDdiMjc5OGMzYWQxYzU4N2Y2NzQyZDAzMjIzNGI0ZCIsIm5iZiI6MTc2MzM4NzI1NC4wMzcsInN1YiI6IjY5MWIyNzc2MDI4YWZhYjQ1YTE3NWU2MCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.uw-NBC4BX1xsitfytKFUrgFyrq-m7rdgHeVaRPgyDf0";
        String url = "https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<MovieApiResponse> movieApiResponse = restTemplate.exchange(url, HttpMethod.GET, entity, MovieApiResponse.class);
        System.out.println("accessing....");
        int count = 0;
        for (ResultMovie mp : movieApiResponse.getBody().getResults()) {
            if (count == 1) {
                return;
            }
            NewMovieApiDTO newMovieApiDTO = new NewMovieApiDTO();
            newMovieApiDTO.setTitel(mp.getOriginal_title());
            newMovieApiDTO.setMovieDate(mp.getRelease_date());
            newMovieApiDTO.setImage(mp.getImage());
            insertMoviesFromApi(newMovieApiDTO);
        }
    }

    public void insertMoviesFromApi(NewMovieApiDTO newMovieApiDTO) {
        System.out.println(newMovieApiDTO.getMovieDate());
        List<Movie> newMovie = movieRepository.findByTitel(newMovieApiDTO.getTitel());
        //movie mit hall und welche zeit und tag

        if (newMovie.size() == 0) {
            Movie movie = new Movie();
            movie.setTitel(newMovieApiDTO.getTitel());
            movie.setImage(newMovieApiDTO.getImage());
            Movie insertedMovie = movieRepository.save(movie);

            Hall hall = hallRepository.getById(1L); //flo opder juergen fragen ob random?


            MovieShowTime movieShowTime = new MovieShowTime();
            movieShowTime.setMovie_id(insertedMovie);
            movieShowTime.setShow_date(newMovieApiDTO.getMovieDate());
            movieShowTime.setShow_time(Time.valueOf("18:30:00"));//hier auch nochaml fragen
            //vllt entscheinde welcher film um welche zeit nach feld popularity in der api response
            //todo: schauen ob um diese zeit an diesen tag schon ein film in der hall laefuft
            movieShowTime.setHall(hall);
            movieShowTimeRepository.save(movieShowTime);
        }
    }
}
