package com.kinosoftware.backend.Service;

import com.kinosoftware.backend.DTO.ReservationDTO;
import com.kinosoftware.backend.DTO.SeatsDTO;
import com.kinosoftware.backend.Entity.*;
import com.kinosoftware.backend.Repository.MovieRepository;
import com.kinosoftware.backend.Repository.ReservationRepository;
import com.kinosoftware.backend.Repository.ReservationSeatsRepository;
import com.kinosoftware.backend.Repository.SeatsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ReservationService {

    private final MovieRepository movieRepository;
    private final ReservationRepository reservationRepository;
    private final SeatsRepository seatsRepository;
    private final ReservationSeatsRepository reservationSeatsRepository;


    public ReservationService(MovieRepository movieRepository, ReservationRepository reservationRepository, SeatsRepository seatsRepository, ReservationSeatsRepository reservationSeatsRepository) {
        this.movieRepository = movieRepository;
        this.reservationRepository = reservationRepository;
        this.seatsRepository = seatsRepository;
        this.reservationSeatsRepository = reservationSeatsRepository;
    }

    public ResponseEntity<?> buyMovieTicekts(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();
        reservation.setCustomerName(reservationDTO.getCustomerName());
        reservation.setReservationTime(reservationDTO.getReservationTime());

        Movie movie = movieRepository.findById(reservationDTO.getMovieId()).orElseThrow(() -> new RuntimeException("Movie not found"));

        reservation.setMovieId(movie);

        reservationRepository.save(reservation);


        System.out.println(movie.getHall());



        for (SeatsDTO i : reservationDTO.getSeats()) {
            Seats seats = new Seats();
            ReservationSeats reservationSeats = new ReservationSeats();
            seats.setHall(movie.getHall());
            //System.out.println(i.getRow_num());
            //System.out.println(i);
            seats.setRowNum(i.getRow_num());
            seats.setSeatNum(i.getSeat_num());
            seats.setStatus(Status.Booked);
            seatsRepository.save(seats);
            System.out.println(seats.getStatus());
            reservationSeats.setReservationID(reservation);
            reservationSeats.setSeatId(seats);
            reservationSeatsRepository.save(reservationSeats);
        }

        Map<String, Object> response = new HashMap<>();

        response.put("Saved new Movie buy", reservationDTO);
        return ResponseEntity.ok().body(response);
    }
}
