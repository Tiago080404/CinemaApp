package com.kinosoftware.backend.DTO;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ReservationDTO {

    private String customerName;
    private LocalDateTime reservationTime;
    private Long movieId;
    private List<SeatsDTO> seats;//das muss ne liste seinJackson versucht trotzdem, JSON zu deserialisieren → Feldnamen stimmen oft nicht überein (row_num ≠ rowNum).
}
