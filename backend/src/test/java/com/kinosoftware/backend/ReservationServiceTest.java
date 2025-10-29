package com.kinosoftware.backend;

import com.kinosoftware.backend.DTO.ReservationDTO;
import com.kinosoftware.backend.DTO.SeatsDTO;
import com.kinosoftware.backend.Entity.Hall;
import com.kinosoftware.backend.Entity.Movie;
import com.kinosoftware.backend.Repository.MovieRepository;
import com.kinosoftware.backend.Repository.ReservationRepository;
import com.kinosoftware.backend.Repository.ReservationSeatsRepository;
import com.kinosoftware.backend.Repository.SeatsRepository;
import com.kinosoftware.backend.Service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ReservationServiceTest {

    @Mock
    MovieRepository movieRepository;

    @Mock
    ReservationRepository reservationRepository;

    @Mock
    SeatsRepository seatsRepository;

    @Mock
    ReservationSeatsRepository reservationSeatsRepository;

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

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        ReservationDTO dto = new ReservationDTO();
        dto.setMovieId(1L);
        dto.setCustomerName("Max Mustermann");
        dto.setReservationTime(LocalDateTime.now());
        dto.setSeats(List.of(new SeatsDTO(1, 1), new SeatsDTO(1, 2)));

        ResponseEntity<?> response = reservationService.buyMovieTicekts(dto);

        assertEquals(200, response.getStatusCodeValue());
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

        ResponseEntity<?> response = reservationService.buyMovieTicekts(dto);

        assertEquals(403, response.getStatusCodeValue());
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
        dto.setSeats(List.of(new SeatsDTO(1, 1), new SeatsDTO(1, 2)));

        ResponseEntity<?> response = reservationService.buyMovieTicekts(dto);

        assertEquals(403, response.getStatusCodeValue());
    }
}
