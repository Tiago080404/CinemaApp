package com.kinosoftware.backend.DTO.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class AllMoviesWithDateResponse {
    private int movieId;
    private String titel;
    private String image;
    private BigDecimal popularity;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Europe/Berlin")
    private List<Date> showDate;



    public AllMoviesWithDateResponse(int movieId, String titel, String image,BigDecimal popularity, List<Date> showDate) {
        this.movieId = movieId;
        this.titel = titel;
        this.image = image;
        this.popularity = popularity;
        this.showDate = showDate;
    }
}
