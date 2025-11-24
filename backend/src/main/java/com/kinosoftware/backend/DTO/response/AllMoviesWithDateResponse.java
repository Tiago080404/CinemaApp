package com.kinosoftware.backend.DTO.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AllMoviesWithDateResponse {
    private int movieId;
    private String titel;
    private String image;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Europe/Berlin")
    private List<Date> showDate;

    public AllMoviesWithDateResponse(int movieId, String titel, String image, List<Date> showDate) {
        this.movieId = movieId;
        this.titel = titel;
        this.image = image;
        this.showDate = showDate;
    }
}
