package com.kinosoftware.backend.DTO.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kinosoftware.backend.Entity.Hall;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MovieResponse {

    private Long movieId;
    private String titel;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime movieDate;
    private Hall hall;

    public MovieResponse(Long movieId, String titel, LocalDateTime movieDate, Hall hall) {
        this.movieId = movieId;
        this.titel = titel;
        this.movieDate = movieDate;
        this.hall = hall;
    }
}
