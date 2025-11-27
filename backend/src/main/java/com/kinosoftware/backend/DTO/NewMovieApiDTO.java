package com.kinosoftware.backend.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class NewMovieApiDTO {

    private String titel;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate movieDate;
    private Long hall;//das vielleicht raus!!!!!
    private String image;
    private BigDecimal popularity;
}
