package com.kinosoftware.backend;

import com.kinosoftware.backend.DTO.ReservationDTO;
import com.kinosoftware.backend.DTO.SeatsDTO;
import com.kinosoftware.backend.Entity.*;
import com.kinosoftware.backend.Repository.*;
import com.kinosoftware.backend.Service.ReservationService;
import org.apache.coyote.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReservationServiceTest {

    @Mock
    MovieRepository movieRepository;

    @Mock
    ReservationRepository reservationRepository;

    @Mock
    SeatsRepository seatsRepository;

    @Mock
    ReservationSeatsRepository reservationSeatsRepository;

    @Mock
    MovieSeatsStatusRepository movieSeatsStatusRepository;

    @InjectMocks
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void buyTicket() {
        Movie movie = new Movie();
        movie.setMovieId(1L);
        movie.setTitel("Inception");
        movie.setMovieDate(LocalDateTime.now());
        Hall hall = new Hall();
        hall.setHallId(1L);
        movie.setHall(hall);
        Seats seats = new Seats();
        seats.setHall(hall);
        seats.setSeatsId(1L);
        seats.setSeatNum(1);
        seats.setRowNum(1);

        Seats seats2 = new Seats();
        seats2.setHall(hall);
        seats2.setSeatsId(2L);
        seats2.setSeatNum(2);
        seats2.setRowNum(1);


        MovieSeatStatus movieSeatStatus = new MovieSeatStatus();
        movieSeatStatus.setSeatId(seats);
        MovieSeatStatus movieSeatStatus2 = new MovieSeatStatus();
        movieSeatStatus2.setSeatId(seats);



        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(movieSeatsStatusRepository.findById(1L)).thenReturn(Optional.of(movieSeatStatus));
        when(movieSeatsStatusRepository.findById(1L)).thenReturn(Optional.of(movieSeatStatus2));

        ReservationDTO dto = new ReservationDTO();
        dto.setMovieId(1L);
        dto.setCustomerName("Max Mustermann");
        dto.setReservationTime(LocalDateTime.now().minusMinutes(120));
        dto.setSeats(Arrays.asList(new SeatsDTO(1, 1), new SeatsDTO(1, 2)));

        ReservationDTO response = reservationService.buyMovieTicekts(dto);
        assertEquals(dto,response);
    }

    @Test
    void moreThanTenTickets() {
        Movie movie = new Movie();
        movie.setMovieId(2L);
        movie.setTitel("Inception");
        movie.setMovieDate(LocalDateTime.now());
        Hall hall = new Hall();
        hall.setHallId(2L);
        hall.setTotalRows(20);
        hall.setSeatsPerRow(25);
        movie.setHall(hall);

        when(movieRepository.findById(2L)).thenReturn(Optional.of(movie));


        ReservationDTO dto = new ReservationDTO();
        dto.setMovieId(2L);
        dto.setCustomerName("Test User");
        dto.setReservationTime(LocalDateTime.now());

        ArrayList<SeatsDTO> toManySeats = new ArrayList<>();

        for (int i = 0; i < 11; i++) {
            toManySeats.add(new SeatsDTO(1, i + 1));
        }
        dto.setSeats(toManySeats);

        ReservationDTO response = reservationService.buyMovieTicekts(dto);
        assertEquals(null,response);
    }

    @Test
    void toLateForReservate() {
        Movie movie = new Movie();
        movie.setMovieId(3L);
        movie.setTitel("badmovie");
        movie.setMovieDate(LocalDateTime.now());
        Hall hall = new Hall();
        hall.setHallId(2L);
        hall.setTotalRows(20);
        hall.setSeatsPerRow(25);
        movie.setHall(hall);

        when(movieRepository.findById(3L)).thenReturn(Optional.of(movie));

        ReservationDTO dto = new ReservationDTO();
        dto.setMovieId(3L);
        dto.setReservationTime(LocalDateTime.now());

        dto.setCustomerName("tiago");
        dto.setSeats(Arrays.asList(new SeatsDTO(1, 1), new SeatsDTO(1, 2)));

        ReservationDTO response = reservationService.buyMovieTicekts(dto);
        assertEquals(null,response);
    }

    @Test
    void moreThanSevenDaysInFuture() {
        Movie movie = new Movie();
        movie.setMovieId(3L);
        movie.setTitel("badmovie");
        movie.setMovieDate(LocalDateTime.now().plusDays(8));
        Hall hall = new Hall();
        hall.setHallId(2L);
        hall.setTotalRows(20);
        hall.setSeatsPerRow(25);
        movie.setHall(hall);

        when(movieRepository.findById(3L)).thenReturn(Optional.of(movie));

        ReservationDTO dto = new ReservationDTO();
        dto.setReservationTime(LocalDateTime.now());
        dto.setMovieId(3L);
        dto.setCustomerName("tiago");
        dto.setSeats(Collections.unmodifiableList(Arrays.asList(new SeatsDTO(1, 1), new SeatsDTO(1, 2))));

        ReservationDTO response = reservationService.buyMovieTicekts(dto);
        assertEquals(null,response);
    }

    @Test
    void checkIfSeatsAvailable() {
        Movie movie = new Movie();
        movie.setMovieId(3L);
        movie.setTitel("badmovie");
        movie.setMovieDate(LocalDateTime.now().plusDays(8));
        Hall hall = new Hall();
        hall.setHallId(2L);
        hall.setTotalRows(20);
        hall.setSeatsPerRow(25);
        movie.setHall(hall);
        Seats seats = new Seats();
        seats.setSeatNum(1);
        seats.setRowNum(1);
        //seats.setStatus(Status.Booked);

        //MovieSeatStatus movieSeatStatus = new MovieSeatStatus();


        when(movieRepository.findById(3L)).thenReturn(Optional.of(movie));

        when(reservationSeatsRepository.checkAvaialbleSeats(3L, 1, 1))
                .thenReturn(Status.valueOf(Status.Booked.toString()));
        when(reservationSeatsRepository.checkAvaialbleSeats(3L, 1, 2))
                .thenReturn(Status.valueOf(Status.Available.toString()));

        ReservationDTO dto = new ReservationDTO();
        dto.setReservationTime(LocalDateTime.now());
        dto.setMovieId(3L);
        dto.setCustomerName("tiago");
        dto.setSeats(Arrays.asList(new SeatsDTO(1, 1), new SeatsDTO(1, 2)));

        ReservationDTO response = reservationService.buyMovieTicekts(dto);

        assertEquals(null, response);


        verify(reservationRepository, never()).save(any());
        verify(seatsRepository, never()).save(any());
        verify(reservationSeatsRepository, never()).save(any(ReservationSeats.class));
    }
}
