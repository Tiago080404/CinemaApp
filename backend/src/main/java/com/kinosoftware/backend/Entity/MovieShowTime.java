package com.kinosoftware.backend.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "movie_showtime")
public class MovieShowTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie_id;

    @ManyToOne
    @JoinColumn(name = "hall")
    private Hall hall;

    @Column
    private LocalDate show_date;
    @Column
    private Time show_time;
}
