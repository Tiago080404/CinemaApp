package com.kinosoftware.backend.DTO.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ResultMovie {
    private String original_language;
    private String original_title;
    private String overview;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate release_date;
    @JsonProperty("poster_path")
    private String image;

    private BigDecimal popularity;
}
