package com.kinosoftware.backend.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
@Getter
@Setter
public class Reservation {

    @Column(name = "reservation_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "reservation_time")
    private LocalDateTime reservationTime;

    @ManyToOne
    @JoinColumn(name = "movie")
    private Movie movieId;
}
