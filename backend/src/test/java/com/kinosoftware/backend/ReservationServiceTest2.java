package com.kinosoftware.backend;

import com.kinosoftware.backend.DTO.ReservationDTO;
import com.kinosoftware.backend.DTO.SeatsDTO;
import com.kinosoftware.backend.DTO.response.ReservationResponse;
import com.kinosoftware.backend.DTO.response.SeatsNotAvailableException;
import com.kinosoftware.backend.Entity.*;
import com.kinosoftware.backend.Repository.MovieRepository;
import com.kinosoftware.backend.Repository.MovieSeatsStatusRepository;
import com.kinosoftware.backend.Repository.MovieShowTimeRepository;
import com.kinosoftware.backend.Repository.ReservationSeatsRepository;
import com.kinosoftware.backend.Service.ReservationService;
import org.junit.jupiter.api.function.Executable;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.scheduling.annotation.Async;
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
    @Autowired
    private MovieShowTimeRepository movieShowTimeRepository;

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

        assertThrows(SeatsNotAvailableException.class, (Executable) reservationService.buyMovieTickets(reservationDTO));
    }

    @Test
    @Sql(scripts = "/movieInsert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void moreThan7Days() {
        ReservationDTO reservationDTO = new ReservationDTO();

        MovieShowTime movieShowTime = movieShowTimeRepository.findById(1L).get();
        reservationDTO.setReservationTime(movieShowTime.getShow_date().minusDays(8).atStartOfDay());
        //reservationDTO.setReservationTime(LocalDateTime.now().minusDays(10L));
        reservationDTO.setCustomerName("tiago");
        reservationDTO.setShowTimeId(1L);
        reservationDTO.setSeats(Arrays.asList(new SeatsDTO(3, 1)));

        assertThrows(SeatsNotAvailableException.class, () -> reservationService.buyMovieTickets(reservationDTO));
    }

    @Test
    @Sql(scripts = "/movieInsert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void oneHourInFutureCheck() {
        ReservationDTO reservationDTO = new ReservationDTO();

        MovieShowTime movieShowTime = movieShowTimeRepository.findById(1L).get();
        System.out.println(movieShowTime.getShow_time());

        movieShowTime.getShow_time().setMinutes(-30);
       String bi = movieShowTime.getShow_date().toString();
       LocalDateTime newDateRes = LocalDateTime.parse(bi +"T"+ movieShowTime.getShow_time());

       reservationDTO.setReservationTime(newDateRes);
       reservationDTO.setCustomerName("tiago");
       reservationDTO.setShowTimeId(1L);
       reservationDTO.setSeats(Arrays.asList(new SeatsDTO(3, 1)));
       assertThrows(SeatsNotAvailableException.class,()->reservationService.buyMovieTickets(reservationDTO));
    }
}
