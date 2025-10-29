package com.kinosoftware.backend.Service;

import com.kinosoftware.backend.DTO.GetMovieSeatsResponseDTO;
import com.kinosoftware.backend.DTO.ReservationDTO;
import com.kinosoftware.backend.DTO.SeatsDTO;
import com.kinosoftware.backend.Entity.*;
import com.kinosoftware.backend.Repository.MovieRepository;
import com.kinosoftware.backend.Repository.ReservationRepository;
import com.kinosoftware.backend.Repository.ReservationSeatsRepository;
import com.kinosoftware.backend.Repository.SeatsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
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

        Map<Integer, Integer> hall = new HashMap<>();
        hall.put(1, 375);
        hall.put(2, 500);
        hall.put(3, 150);

        Movie movie = movieRepository.findById(reservationDTO.getMovieId()).orElseThrow(() -> new RuntimeException("Movie not found"));
        System.out.println("Halllll" + movie.getHall().getHallId());
        Long hallId = movie.getHall().getHallId();
        Integer changedHallIdType = Math.toIntExact(hallId);


        //7 tage in der zukunft max
        //vorsttelung min. 1 Stunde in der zukunft
        System.out.println(movie.getMovieDate());
        int reservationHour = reservationDTO.getReservationTime().getHour();
        int movieHour = movie.getMovieDate().getHours();
        int reservationDate = reservationDTO.getReservationTime().getDayOfMonth();
        int movieDate = movie.getMovieDate().getHours();
        if (movieHour - reservationHour <= 1 && movieDate == reservationDate) {
            Map<String, Object> response = new HashMap<>();
            response.put("Cant buy tickets one hour before movie starting", reservationDTO.getReservationTime());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
        //max10 sizte reservieren
        if (!checkReservationAmount(reservationDTO.getSeats().size())) {
            Map<String, Object> response = new HashMap<>();
            response.put("Can only buy 10 tickets", reservationDTO.getSeats().size());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }


        //sitze frei sind done!!!
        List<GetMovieSeatsResponseDTO> movieSeatsResponseDTO = reservationRepository.getMovieSeats(reservationDTO.getMovieId());
        System.out.println(movieSeatsResponseDTO);
        int availableSeats = hall.get(changedHallIdType);
        for (GetMovieSeatsResponseDTO movieDto : movieSeatsResponseDTO) {
            System.out.println(movieDto.getTitel() + movieDto.getRow_num() + movieDto.getSeat_num());
            availableSeats--;
        }
        System.out.println("seats available"+availableSeats);
        //test case:
        //int testAvailable = availableSeats - 365;
        if (availableSeats - reservationDTO.getSeats().size() <= 0) {
            Map<String, Object> response = new HashMap<>();
            response.put("Message", "This amount is not available");
            response.put("Amount", reservationDTO.getSeats().size());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }


        Reservation reservation = new Reservation();
        reservation.setCustomerName(reservationDTO.getCustomerName());
        reservation.setReservationTime(reservationDTO.getReservationTime());

        //Movie movie = movieRepository.findById(reservationDTO.getMovieId()).orElseThrow(() -> new RuntimeException("Movie not found"));
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


            reservationSeats.setReservationID(reservation);
            reservationSeats.setSeatId(seats);

            reservationSeatsRepository.save(reservationSeats);
        }

        Map<String, Object> response = new HashMap<>();

        response.put("Saved new Movie buy", reservationDTO);
        return ResponseEntity.ok().body(response);
    }

    public boolean checkReservationAmount(int ticketsAmount) {
        if (ticketsAmount > 10) {
            return false;
        }
        return true;
    }
}
