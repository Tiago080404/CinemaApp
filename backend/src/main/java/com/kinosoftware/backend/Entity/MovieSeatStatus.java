package com.kinosoftware.backend.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "movie_seat_status")
@Getter
@Setter
@IdClass(MovieSeatStatusId.class)
public class MovieSeatStatus {

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_id")
    private Movie movieId;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "seat_id")
    private Seats seatId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private Status status;
}
