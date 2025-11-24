package com.kinosoftware.backend.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
public class AllMoviesWithDateDTO {
    private int movieId;
    private String titel;
    private String image;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Europe/Berlin")
    private Date showDate;

    public AllMoviesWithDateDTO(int movieId, String titel, String image, Date showDate) {
        this.movieId = movieId;
        this.titel = titel;
        this.image = image;
        this.showDate = showDate;
    }
}
