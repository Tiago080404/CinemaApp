package com.kinosoftware.backend.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "movie")
@Getter
@Setter
public class Movie {

    @Id
    @Column(name = "movie_id")
    private Long movieId;

    @Column(name = "titel")
    private String titel;

    @Column(name = "movie_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime movieDate;

    @ManyToOne
    @JoinColumn(name = "hall")
    private Hall hall;

}
