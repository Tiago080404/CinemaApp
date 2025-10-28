package com.kinosoftware.backend.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "seats")
@Getter
@Setter
public class Seats {

    @Id
    @Column(name = "seats_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatsId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hall_id")
    private Hall hall;

    @Column(name = "row_num")
    private int rowNum;

    @Column(name = "seat_num")
    private int seatNum;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private Status status;
}
