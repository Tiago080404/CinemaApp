package com.kinosoftware.backend.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate show_date;
    @Column
    private Time show_time;
}
