package com.kinosoftware.backend.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    private Date movieDate;

    @ManyToOne
    @JoinColumn(name = "hall")
    private Hall hall;

}
