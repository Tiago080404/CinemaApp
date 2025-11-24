package com.kinosoftware.backend.Service;

import com.kinosoftware.backend.DTO.AllMoviesWithDateDTO;
import com.kinosoftware.backend.DTO.AllSeatsForMovieDTO;
import com.kinosoftware.backend.DTO.NewMovieApiDTO;
import com.kinosoftware.backend.DTO.NewMovieDTO;
import com.kinosoftware.backend.DTO.response.AllMoviesWithDateResponse;
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
import java.time.LocalDate;
import java.util.*;

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

    public List<AllMoviesWithDateResponse> getAllMovies() {

        List<AllMoviesWithDateDTO> movies = movieRepository.getAllMoviesWithDate();
        List<AllMoviesWithDateResponse> newCheck = new ArrayList<>();

        for (int i = 0; i < movies.size(); i++) {
            System.out.println("dafs" + movies.get(i));
            AllMoviesWithDateResponse existing = null;
            for (AllMoviesWithDateResponse response : newCheck ){
                if(response.getMovieId()==movies.get(i).getMovieId()){
                    existing = response;
                    break;
                }
            }
            if(existing!=null){
                existing.getShowDate().add(movies.get(i).getShowDate());
            }else {
                AllMoviesWithDateResponse newMovie = new AllMoviesWithDateResponse(
                        movies.get(i).getMovieId(),
                        movies.get(i).getTitel(),
                        movies.get(i).getImage(),
                        new ArrayList<Date>()
                );
                newMovie.getShowDate().add(movies.get(i).getShowDate());
                newCheck.add(newMovie);
            }
        }

        return newCheck;
    }

    public Optional<MovieResponse> getMovieById(Long id) {
        return movieRepository.findById(id).map(movie -> new MovieResponse(
                movie.getMovieId(),
                movie.getTitel()
                //movie.getMovieDate()
                //movie.getHall()
        ));
    }

    public List<AllSeatsForMovieDTO> getAllSeatsForMovie(Long showTimeId) {
        return movieRepository.getAllSeatsFromMovie(showTimeId);
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
        List<MovieShowTime> movieShowTimes = movieShowTimeRepository.findByShowDate(newMovieApiDTO.getMovieDate());


        List<String> times = new ArrayList<>();
        times.add("20:00:00");
        times.add("14:00:00");
        times.add("16:30:00");

        Map<Long, List<String>> halls = new HashMap<>();
        halls.put(1L, new ArrayList<>());
        halls.put(2L, new ArrayList<>());
        halls.put(3L, new ArrayList<>());

        if (movieShowTimes.size() == 0) {
            System.out.println("is empty");
            int randNum = (int) ((Math.random() * (3 - 0)) + 0);
            String randTime = times.get(randNum);
            Movie movie = new Movie();
            movie.setTitel(newMovieApiDTO.getTitel());
            movie.setImage(newMovieApiDTO.getImage());
            Movie insertedMovie = movieRepository.save(movie);//vllt noch check ob der movie schon existeier
            MovieShowTime movieShowTime = new MovieShowTime();
            movieShowTime.setMovie_id(insertedMovie);
            movieShowTime.setShow_date(newMovieApiDTO.getMovieDate());
            movieShowTime.setShow_time(Time.valueOf(randTime));
            Hall hall = hallRepository.getById(1L);
            movieShowTime.setHall(hall);
            movieShowTimeRepository.save(movieShowTime);
            return;
        }

        for (MovieShowTime movieShowTime : movieShowTimes) {
            List<String> hallTimes = halls.get(movieShowTime.getHall().getHallId());
            hallTimes.add(String.valueOf(movieShowTime.getShow_time()));
        }


        for (Long i = 1L; i <= halls.size(); i++) {

            List<String> specificHall = halls.get(i);

            for (int timeindex = 0; timeindex < 3; timeindex++) {
                int randNum = (int) ((Math.random() * (3 - 0)) + 0);
                String randTime = times.get(randNum);
                if (specificHall.contains(randTime)) {
                    System.out.println("exists already");

                } else {
                    specificHall.add(randTime);

                    Hall hall = hallRepository.getById(i);


                    Movie movie = new Movie();
                    movie.setTitel(newMovieApiDTO.getTitel());
                    movie.setImage(newMovieApiDTO.getImage());
                    Movie safedMovie = movieRepository.save(movie);
                    MovieShowTime movieShowTime = new MovieShowTime();
                    movieShowTime.setShow_date(newMovieApiDTO.getMovieDate());
                    movieShowTime.setMovie_id(safedMovie);
                    movieShowTime.setShow_time(Time.valueOf(randTime));
                    movieShowTime.setHall(hall);
                    movieShowTimeRepository.save(movieShowTime);
                    return;
                }

            }

        }
    }

    public List<MovieShowTime> getAllShowsForMovie(Long movieId) {
        List<MovieShowTime> movieShowTimes = movieShowTimeRepository.getAllShowsForMovie(movieId);
        if (movieShowTimes.isEmpty()) {
            return null;
        } else {
            return movieShowTimes;
        }
    }
}
