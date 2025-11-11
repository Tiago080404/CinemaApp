package com.kinosoftware.backend.DTO.response;

import com.kinosoftware.backend.DTO.SeatsDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReservationResponse {
    private String customerName;
    private LocalDateTime reservationTime;
    private Long movieId;
    private List<SeatsDTO> seats;

    public ReservationResponse(String customerName, LocalDateTime reservationTime, Long movieId, List<SeatsDTO> seats) {
        this.customerName = customerName;
        this.reservationTime = reservationTime;
        this.movieId = movieId;
        this.seats = seats;
    }
}
