package com.kinosoftware.backend.Controller;

import com.kinosoftware.backend.DTO.ReservationDTO;
import com.kinosoftware.backend.Service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/reservate")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<?>buyMovieTickets(@RequestBody ReservationDTO reservationDTO){
        return reservationService.buyMovieTicekts(reservationDTO);

    }
}
