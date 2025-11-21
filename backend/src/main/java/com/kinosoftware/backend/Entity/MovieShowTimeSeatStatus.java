package com.kinosoftware.backend.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Data
@Entity
@Table(name = "movie_showtime_seat_status")
@IdClass(MovieShowTimeSeatStatusId.class)

public class MovieShowTimeSeatStatus {

    @Id
    @ManyToOne
    @JoinColumn(name = "showtime_id")
    private MovieShowTime movieShowTime;

    @Id
    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seats seats;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private Status status;

}
