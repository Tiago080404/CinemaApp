package com.kinosoftware.backend.Controller;

import com.kinosoftware.backend.DTO.ReservationDTO;
import com.kinosoftware.backend.DTO.response.ReservationResponse;
import com.kinosoftware.backend.Service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController()
@RequestMapping("/reservate")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<?> buyMovieTickets(@RequestBody ReservationDTO reservationDTO) {
        ReservationResponse reservation = reservationService.buyMovieTickets(reservationDTO);
        Map<String, Object> response = new HashMap<>();
        if (reservation == null) {

            response.put("message", "Cant reservate Tickets!");
            response.put("Reservation", reservationDTO);

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        response.put("message", "Reservated tickets");
        response.put("Reservation", reservationDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
