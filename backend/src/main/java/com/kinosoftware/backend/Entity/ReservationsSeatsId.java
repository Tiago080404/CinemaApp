package com.kinosoftware.backend.Entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ReservationsSeatsId implements Serializable {
    private Reservation reservationID;
    private Seats seatId;
}
