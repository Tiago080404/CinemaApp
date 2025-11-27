package com.kinosoftware.backend.Service;

import com.kinosoftware.backend.DTO.ReservationDTO;
import com.kinosoftware.backend.DTO.SeatsDTO;
import com.kinosoftware.backend.DTO.response.ReservationResponse;
import com.kinosoftware.backend.DTO.response.SeatsNotAvailableException;
import com.kinosoftware.backend.Entity.*;
import com.kinosoftware.backend.Repository.*;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.sql.Time;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class ReservationService {

    private final MovieRepository movieRepository;
    private final ReservationRepository reservationRepository;
    private final SeatsRepository seatsRepository;
    private final ReservationSeatsRepository reservationSeatsRepository;
    private final MovieSeatsStatusRepository movieSeatsStatusRepository;
    private final MovieShowTimeRepository movieShowTimeRepository;
    private final MovieShowTimeSeatStatusRepository movieShowTimeSeatStatusRepository;

    public ReservationService(MovieRepository movieRepository, ReservationRepository reservationRepository, SeatsRepository seatsRepository, ReservationSeatsRepository reservationSeatsRepository, MovieSeatsStatusRepository movieSeatsStatusRepository, MovieShowTimeRepository movieShowTimeRepository, MovieShowTimeSeatStatusRepository movieShowTimeSeatStatusRepository) {
        this.movieRepository = movieRepository;
        this.reservationRepository = reservationRepository;
        this.seatsRepository = seatsRepository;
        this.reservationSeatsRepository = reservationSeatsRepository;
        this.movieSeatsStatusRepository = movieSeatsStatusRepository;
        this.movieShowTimeRepository = movieShowTimeRepository;
        this.movieShowTimeSeatStatusRepository = movieShowTimeSeatStatusRepository;
    }

//    public ReservationResponse buyMovieTicekts(ReservationDTO reservationDTO) {
//        //sitze frei
//        ArrayList<Map> bookedSeats = checkSeatsAvailable(reservationDTO);
//        if (bookedSeats.size() != 0) {
//            Map<String, Object> response = new HashMap<>();
//            for (int i = 0; i < bookedSeats.size(); i++) {
//
//                response.put("Seats are not available", bookedSeats.get(i));
//
//            }
//            throw new SeatsNotAvailableException("Seats are not available");
//        }
//
//
//        Map<Integer, Integer> hall = new HashMap<>();
//        hall.put(1, 375);
//        hall.put(2, 500);
//        hall.put(3, 150);
//
//        Movie movie = movieRepository.findById(reservationDTO.getMovieId()).orElseThrow(() -> new RuntimeException("Movie not found"));
//      //  System.out.println("Halllll" + movie.getHall().getHallId());
//       // Long hallId = movie.getHall().getHallId();
//        //Integer changedHallIdType = Math.toIntExact(hallId);
//
//
//        //vorsttelung min. 1 Stunde in der zukunft
//        //System.out.println(movie.getMovieDate());
//        System.out.println(reservationDTO.getReservationTime());
//
////        if (!oneHourInFuture(reservationDTO, movie)) {
////            throw new SeatsNotAvailableException("Movie is in one hour cant book anymore!");
////
////        }
//
//

    /// /        if (!sevenDaysInFuture(reservationDTO, movie)) {
    /// /            throw new SeatsNotAvailableException("Movie is in more than 7 days cant book!");
    /// /        }
//
//
//        //max10 sizte reservieren
//        if (!checkReservationAmount(reservationDTO.getSeats().size())) {
//            throw new SeatsNotAvailableException("Cant book more than 10 seats!");
//        }
//
//
//        Reservation reservation = new Reservation();
//        reservation.setCustomerName(reservationDTO.getCustomerName());
//        reservation.setReservationTime(reservationDTO.getReservationTime());
//
//        reservation.setMovieId(movie);
//
//        reservationRepository.save(reservation);
//
//
//
//
//        for (SeatsDTO i : reservationDTO.getSeats()) {
//            MovieSeatStatus movieSeatStatus = new MovieSeatStatus();
//
//            Seats seats = seatsRepository.findbySeatAndRowNum(i.getRow_num(), i.getSeat_num());
//
//
//            ReservationSeats reservationSeats = new ReservationSeats();
//
//
//            movieSeatStatus.setMovieId(movie);
//            movieSeatStatus.setSeatId(seats);
//            movieSeatStatus.setStatus(Status.Booked);
//
//
//            reservationSeats.setReservationID(reservation);
//            reservationSeats.setSeatId(seats);
//
//            reservationSeatsRepository.save(reservationSeats);
//            movieSeatsStatusRepository.save(movieSeatStatus);
//        }
//
//        return new ReservationResponse(
//                reservationDTO.getCustomerName(),
//                reservationDTO.getReservationTime(),
//                reservationDTO.getMovieId(),
//                reservationDTO.getSeats()
//        );
//        //return reservationDTO;
//    }
//
//    public boolean checkReservationAmount(int ticketsAmount) {
//        if (ticketsAmount > 10) {
//            return false;
//        }
//        return true;
//    }
//
//    public ArrayList<Map> checkSeatsAvailable(ReservationDTO reservationDTO) {//damit anfangen dto neues feld wahrscheinlich f[er movieshowtime und date zwei felder vllt
//        ArrayList<Map> bookedSeats = new ArrayList<>();
//        for (SeatsDTO seats : reservationDTO.getSeats()) {
//            Status seatStatus = reservationSeatsRepository.checkAvaialbleSeats(reservationDTO.getMovieId(), seats.getRow_num(), seats.getSeat_num());//vllt umbennen in seatstatus
//            System.out.println("statusss" + seatStatus);
//            if (seatStatus == null) {
//                continue;
//            }
//            if (seatStatus.toString() == Status.Booked.toString()) {
//                Map<Integer, Integer> bookedSeatsMap = new HashMap<>();
//                bookedSeatsMap.put(seats.getRow_num(), seats.getSeat_num());
//                bookedSeats.add(bookedSeatsMap);
//            }
//        }
//        return bookedSeats;
//    }

//    public boolean oneHourInFuture(ReservationDTO reservationDTO, Movie movie) {
//        int reservationHour = reservationDTO.getReservationTime().getHour();
//        int movieHour = movie.getMovieDate().getHour();
//
//        int reservationDate = reservationDTO.getReservationTime().getDayOfMonth();
//        int movieDate = movie.getMovieDate().getDayOfMonth();
//
//        int movieMonth = movie.getMovieDate().getMonthValue();
//        int reservationMonth = reservationDTO.getReservationTime().getMonthValue();
//
//        if (movieHour - reservationHour <= 1 && movieDate == reservationDate && movieMonth == reservationMonth) {
//            return false;
//        }
//        return true;
//    }

//    public boolean sevenDaysInFuture(ReservationDTO reservationDTO, Movie movie) {
//        int reservationDate = reservationDTO.getReservationTime().getDayOfMonth();
//        int movieDate = movie.getMovieDate().getDayOfMonth();
//
//        int movieMonth = movie.getMovieDate().getMonthValue();
//        int reservationMonth = reservationDTO.getReservationTime().getMonthValue();
//        System.out.println("minus");
//        System.out.println(movieDate - reservationDate);
//        if (movieDate - reservationDate > 7) {
//            return false;
//        }
//        if (movieMonth != reservationMonth) {//hier vllt noch für ungleiches jahr
//            return false;
//        }
//
//        return true;
//    }
    @Transactional
    public ReservationResponse buyMovieTickets(ReservationDTO reservationDTO) {
        //sitze frei
        ArrayList<Map> statusList = checkSeatStatus(reservationDTO);
        if (statusList.size() != 0) {
            Map<String, Object> response = new HashMap<>();
            for (int i = 0; i < statusList.size(); i++) {

                response.put("Seats are not available", statusList.get(i));
            }
            throw new SeatsNotAvailableException("Seats are not available");
        }
        System.out.println("accessingggg...");

        MovieShowTime movieShowTime = movieShowTimeRepository.getById(reservationDTO.getShowTimeId());
        System.out.println("Moviesdhowtime" + movieShowTime);
        //onehour
        if (!oneHourInFutureCheck(reservationDTO, movieShowTime)) {
            throw new SeatsNotAvailableException("Movie is in one hour cant book anymore!");
        }

        //sevendays
        if (!sevenDaysInFutureCheck(reservationDTO, movieShowTime)) {
            throw new SeatsNotAvailableException("Movie is 7 days in the future!");

        }

        //10 tickets maxx
        if (!checkSeatsReservationAmount(reservationDTO.getSeats().size())) {
            throw new SeatsNotAvailableException("Cant book more than 10 seats!");
        }

        Reservation reservation = new Reservation();
        reservation.setCustomerName(reservationDTO.getCustomerName());
        reservation.setReservationTime(reservationDTO.getReservationTime());

        reservation.setMovieShowTime(movieShowTime);
        reservationRepository.save(reservation);
        //noch insrt
        for (SeatsDTO i : reservationDTO.getSeats()) {
            MovieShowTimeSeatStatus movieShowTimeSeatStatus = new MovieShowTimeSeatStatus();
            Seats seats = seatsRepository.findbySeatAndRowNum(i.getRow_num(), i.getSeat_num());

            movieShowTimeSeatStatus.setMovieShowTime(movieShowTime);
            movieShowTimeSeatStatus.setSeats(seats);
            movieShowTimeSeatStatus.setStatus(Status.Booked);
            System.out.println("movieShowtime" + movieShowTimeSeatStatus);
            ReservationSeats reservationSeats = new ReservationSeats();
            reservationSeats.setReservationID(reservation);
            reservationSeats.setSeatId(seats);
            reservationSeatsRepository.save(reservationSeats);
            movieShowTimeSeatStatusRepository.save(movieShowTimeSeatStatus);
        }


        return new ReservationResponse(
                reservationDTO.getCustomerName(),
                reservationDTO.getReservationTime(),
                reservationDTO.getShowTimeId(),
                reservationDTO.getSeats()
        );
    }

    public ArrayList<Map> checkSeatStatus(ReservationDTO reservationDTO) {

        ArrayList<Map> bookedSeats = new ArrayList<>();
        for (SeatsDTO seatsDTO : reservationDTO.getSeats()) {
            Status seatStatus = reservationSeatsRepository.checkAvailableSeats(reservationDTO.getShowTimeId(), seatsDTO.getRow_num(), seatsDTO.getSeat_num());
            System.out.println(seatStatus);
            if (seatStatus == null) {
                continue;
            }
            if (seatStatus.toString() == Status.Booked.toString()) {
                Map<Integer, Integer> bookedSeatsMap = new HashMap<>();
                bookedSeatsMap.put(seatsDTO.getRow_num(), seatsDTO.getSeat_num());
                bookedSeats.add(bookedSeatsMap);
            }
        }
        return bookedSeats;
    }

    public boolean oneHourInFutureCheck(ReservationDTO reservationDTO, MovieShowTime movieShowTime) {
        int reservationHour = reservationDTO.getReservationTime().getHour();
        int movieShowTimeHour = movieShowTime.getShow_time().getHours();

        int reservationDate = reservationDTO.getReservationTime().getDayOfMonth();
        int movieShowTimeDate = movieShowTime.getShow_date().getDayOfMonth();

        int reservationMonth = reservationDTO.getReservationTime().getMonthValue();
        int movieShowTimeMonth = movieShowTime.getShow_date().getMonthValue();

        if (movieShowTimeHour - reservationHour <= 1 && reservationDate == movieShowTimeDate && reservationMonth == movieShowTimeMonth) {
            return false;
        }
        return true;
    }

    public boolean sevenDaysInFutureCheck(ReservationDTO reservationDTO, MovieShowTime movieShowTime) {
        LocalDate reservationDate = reservationDTO.getReservationTime().toLocalDate();
        LocalDate movieShowTimeDate = movieShowTime.getShow_date();

        Long diff = ChronoUnit.DAYS.between(reservationDate, movieShowTimeDate);

        return diff <= 7;
    }

    public boolean checkSeatsReservationAmount(int ticketAmount) {
        return ticketAmount <= 10;
    }


    public int getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();

        int reservationSize = reservations.size();
        return reservationSize;
    }

    public int thisDayReservations() {
        List<Reservation> reservations = reservationRepository.reservationToday();
        return reservations.size();
    }
}
