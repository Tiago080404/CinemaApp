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
    private Long showTimeId;
    private List<SeatsDTO> seats;
}
