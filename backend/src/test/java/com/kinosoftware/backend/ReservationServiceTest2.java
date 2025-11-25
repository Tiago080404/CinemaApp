package com.kinosoftware.backend;

import com.kinosoftware.backend.DTO.ReservationDTO;
import com.kinosoftware.backend.DTO.SeatsDTO;
import com.kinosoftware.backend.DTO.response.ReservationResponse;
import com.kinosoftware.backend.DTO.response.SeatsNotAvailableException;
import com.kinosoftware.backend.Entity.Hall;
import com.kinosoftware.backend.Entity.Movie;
import com.kinosoftware.backend.Entity.MovieSeatStatus;
import com.kinosoftware.backend.Entity.Seats;
import com.kinosoftware.backend.Repository.MovieRepository;
import com.kinosoftware.backend.Repository.MovieSeatsStatusRepository;
import com.kinosoftware.backend.Repository.ReservationSeatsRepository;
import com.kinosoftware.backend.Service.ReservationService;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class ReservationServiceTest2 {
    //@SQL({"../java/..test/..main/java/resources/db/migration/V1__create_tables.sql"})
    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationSeatsRepository reservationSeatsRepository;

    @Autowired
    private MovieSeatsStatusRepository movieSeatsStatusRepository;

    @Autowired
    private MovieRepository movieRepository;


//    @Test
//    void buyTicket(){
//        ReservationDTO dto = new ReservationDTO();
//        dto.setMovieId(5L);
//        dto.setCustomerName("Max Mustermann");
//        dto.setReservationTime(LocalDateTime.now().minusMinutes(120));
//        dto.setSeats(Arrays.asList(new SeatsDTO(1, 1), new SeatsDTO(1, 2)));
//        System.out.println("dtooo"+dto);
//        ReservationResponse response = reservationService.buyMovieTicekts(dto);
//

    /// /        assertEquals(dto.getCustomerName(), response.getCustomerName());
//        assertEquals(dto.getSeats().size(), response.getSeats().size());
//
//        assertEquals(dto.getCustomerName(),response.getCustomerName());
//    }
//
//    @Test
//    void reservateSameSeat(){
//        ReservationDTO dto = new ReservationDTO();
//        dto.setMovieId(5L);
//        dto.setCustomerName("test22");
//        dto.setReservationTime(LocalDateTime.now().minusMinutes(120));
//        dto.setSeats(Arrays.asList(new SeatsDTO(2, 1), new SeatsDTO(2, 2)));
//        System.out.println("dtooo"+dto);
//        ReservationResponse response = reservationService.buyMovieTicekts(dto);
//
//        ReservationDTO dto2 = new ReservationDTO();
//        dto2.setMovieId(5L);
//        dto2.setCustomerName("Tiago");
//        dto2.setReservationTime(LocalDateTime.now().minusMinutes(120));
//        dto2.setSeats(Arrays.asList(new SeatsDTO(2, 1), new SeatsDTO(2, 2)));
//
//        assertThrows(SeatsNotAvailableException.class,()->reservationService.buyMovieTicekts(dto2));
//    }
//
//    @Test
//    void moreThanTenTickets(){
//        ReservationDTO dto = new ReservationDTO();
//        dto.setMovieId(8L);
//        dto.setCustomerName("Test User");
//        dto.setReservationTime(LocalDateTime.now());
//
//        ArrayList<SeatsDTO> toManySeats = new ArrayList<>();
//
//        for (int i = 0; i < 11; i++) {
//            toManySeats.add(new SeatsDTO(1, i + 1));
//        }
//        dto.setSeats(toManySeats);
//
//        assertThrows(SeatsNotAvailableException.class,()->reservationService.buyMovieTicekts(dto));
//    }
//
//    @Test
//    void toLateForReservate(){}
//
//    @Test
//    //maybe del,ete
//   // @Sql
//    void moreThanSevenDaysInFuture(){
//        Movie movie = movieRepository.findById(1L)
//                .orElseThrow(()->new RuntimeException("Movie not found"));
//       // movie.setMovieDate(LocalDateTime.now().plusDays(8));
//        //System.out.println(movie.getMovieDate());
//        movieRepository.save(movie);
//        ReservationDTO dto = new ReservationDTO();
//        dto.setMovieId(1L);
//        dto.setCustomerName("Test User");
//        dto.setReservationTime(LocalDateTime.now());
//        dto.setSeats(Arrays.asList(new SeatsDTO(2, 1)));
//
//        assertThrows(SeatsNotAvailableException.class,()->reservationService.buyMovieTicekts(dto));
//    }
    @Test
    @Sql(scripts = {"/movieInsert.sql", "/newSeatsReservationsInsert.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void reservateSameSeat() {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setSeats(Arrays.asList(new SeatsDTO(2, 1), new SeatsDTO(2, 2)));
        reservationDTO.setCustomerName("tiago");
        reservationDTO.setReservationTime(LocalDateTime.now());
        reservationDTO.setShowTimeId(1L);

        assertThrows(SeatsNotAvailableException.class, () -> reservationService.buyMovieTickets(reservationDTO));
    }

    @Test
    @Sql(scripts = {"/movieInsert.sql", "/newSeatsReservationsInsert.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(scripts = "/clean.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void moreThanTenTickets() {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setShowTimeId(1L);
        reservationDTO.setCustomerName("tiago");
        reservationDTO.setReservationTime(LocalDateTime.now());
        ArrayList<SeatsDTO> toManySeats = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            toManySeats.add(new SeatsDTO(3, i + 1));
        }
        reservationDTO.setSeats(toManySeats);

        assertThrows(SeatsNotAvailableException.class, () -> reservationService.buyMovieTickets(reservationDTO));
    }
}
