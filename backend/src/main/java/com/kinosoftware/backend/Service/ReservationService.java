package com.kinosoftware.backend.Service;

import com.kinosoftware.backend.DTO.ReservationDTO;
import com.kinosoftware.backend.DTO.SeatsDTO;
import com.kinosoftware.backend.DTO.response.ReservationResponse;
import com.kinosoftware.backend.DTO.response.SeatsNotAvailableException;
import com.kinosoftware.backend.Entity.*;
import com.kinosoftware.backend.Repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReservationService {

    private final MovieRepository movieRepository;
    private final ReservationRepository reservationRepository;
    private final SeatsRepository seatsRepository;
    private final ReservationSeatsRepository reservationSeatsRepository;
    private final MovieSeatsStatusRepository movieSeatsStatusRepository;


    public ReservationService(MovieRepository movieRepository, ReservationRepository reservationRepository, SeatsRepository seatsRepository, ReservationSeatsRepository reservationSeatsRepository, MovieSeatsStatusRepository movieSeatsStatusRepository) {
        this.movieRepository = movieRepository;
        this.reservationRepository = reservationRepository;
        this.seatsRepository = seatsRepository;
        this.reservationSeatsRepository = reservationSeatsRepository;
        this.movieSeatsStatusRepository = movieSeatsStatusRepository;
    }

    public ReservationResponse buyMovieTicekts(ReservationDTO reservationDTO) {
        //sitze frei
        ArrayList<Map> bookedSeats = checkSeatsAvailable(reservationDTO);
        if (bookedSeats.size() != 0) {
            Map<String, Object> response = new HashMap<>();
            for (int i = 0; i < bookedSeats.size(); i++) {

                response.put("Seats are not available", bookedSeats.get(i));

            }
            throw new SeatsNotAvailableException("Seats are not available");
        }


        Map<Integer, Integer> hall = new HashMap<>();
        hall.put(1, 375);
        hall.put(2, 500);
        hall.put(3, 150);

        Movie movie = movieRepository.findById(reservationDTO.getMovieId()).orElseThrow(() -> new RuntimeException("Movie not found"));
        System.out.println("Halllll" + movie.getHall().getHallId());
        Long hallId = movie.getHall().getHallId();
        Integer changedHallIdType = Math.toIntExact(hallId);


        //vorsttelung min. 1 Stunde in der zukunft
        System.out.println(movie.getMovieDate());
        System.out.println(reservationDTO.getReservationTime());

        if (!oneHourInFuture(reservationDTO, movie)) {
            throw new SeatsNotAvailableException("Movie is in one hour cant book anymore!");

        }


        if (!sevenDaysInFuture(reservationDTO, movie)) {
            throw new SeatsNotAvailableException("Movie is in more than 7 days cant book!");
        }


        //max10 sizte reservieren
        if (!checkReservationAmount(reservationDTO.getSeats().size())) {
            throw new SeatsNotAvailableException("Cant book more than 10 seats!");
        }


        Reservation reservation = new Reservation();
        reservation.setCustomerName(reservationDTO.getCustomerName());
        reservation.setReservationTime(reservationDTO.getReservationTime());

        //Movie movie = movieRepository.findById(reservationDTO.getMovieId()).orElseThrow(() -> new RuntimeException("Movie not found"));
        reservation.setMovieId(movie);

        reservationRepository.save(reservation);


        System.out.println(movie.getHall());


        for (SeatsDTO i : reservationDTO.getSeats()) {
            MovieSeatStatus movieSeatStatus = new MovieSeatStatus();

            Seats seats = seatsRepository.findbySeatAndRowNum(i.getRow_num(), i.getSeat_num());


            ReservationSeats reservationSeats = new ReservationSeats();


            movieSeatStatus.setMovieId(movie);
            movieSeatStatus.setSeatId(seats);
            movieSeatStatus.setStatus(Status.Booked);


            reservationSeats.setReservationID(reservation);
            reservationSeats.setSeatId(seats);

            reservationSeatsRepository.save(reservationSeats);
            movieSeatsStatusRepository.save(movieSeatStatus);
        }
        //ReservationResponse reservationResponse;
        //reservationDTO.map()
        return new ReservationResponse(
                reservationDTO.getCustomerName(),
                reservationDTO.getReservationTime(),
                reservationDTO.getMovieId(),
                reservationDTO.getSeats()
        );
        //return reservationDTO;
    }

    public boolean checkReservationAmount(int ticketsAmount) {
        if (ticketsAmount > 10) {
            return false;
        }
        return true;
    }

    public ArrayList<Map> checkSeatsAvailable(ReservationDTO reservationDTO) {
        ArrayList<Map> bookedSeats = new ArrayList<>();
        for (SeatsDTO seats : reservationDTO.getSeats()) {
            Status seatStatus = reservationSeatsRepository.checkAvaialbleSeats(reservationDTO.getMovieId(), seats.getRow_num(), seats.getSeat_num());
            System.out.println("statusss" + seatStatus);
            if (seatStatus == null) {
                continue;
            }
            if (seatStatus.toString() == Status.Booked.toString()) {
                Map<Integer, Integer> bookedSeatsMap = new HashMap<>();
                bookedSeatsMap.put(seats.getRow_num(), seats.getSeat_num());
                bookedSeats.add(bookedSeatsMap);
            }
        }
        return bookedSeats;
    }

    public boolean oneHourInFuture(ReservationDTO reservationDTO, Movie movie) {
        int reservationHour = reservationDTO.getReservationTime().getHour();
        int movieHour = movie.getMovieDate().getHour();

        int reservationDate = reservationDTO.getReservationTime().getDayOfMonth();
        int movieDate = movie.getMovieDate().getDayOfMonth();

        int movieMonth = movie.getMovieDate().getMonthValue();
        int reservationMonth = reservationDTO.getReservationTime().getMonthValue();

        if (movieHour - reservationHour <= 1 && movieDate == reservationDate && movieMonth == reservationMonth) {
            return false;
        }
        return true;
    }

    public boolean sevenDaysInFuture(ReservationDTO reservationDTO, Movie movie) {
        int reservationDate = reservationDTO.getReservationTime().getDayOfMonth();
        int movieDate = movie.getMovieDate().getDayOfMonth();

        int movieMonth = movie.getMovieDate().getMonthValue();
        int reservationMonth = reservationDTO.getReservationTime().getMonthValue();
        System.out.println(movieDate - reservationDate);
        if (movieDate - reservationDate > 7) {
            return false;
        }
        if (movieMonth != reservationMonth) {//hier vllt noch für ungleiches jahr
            return false;
        }

        return true;
    }
}
