package com.kinosoftware.backend.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "reservation_seats")
@Getter
@Setter
@IdClass(ReservationsSeatsId.class)
public class ReservationSeats {

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation_id",referencedColumnName = "reservation_id")
    private Reservation reservationID;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "seat_id")
    private Seats seatId;
}
