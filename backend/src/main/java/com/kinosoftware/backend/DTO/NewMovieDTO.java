package com.kinosoftware.backend.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kinosoftware.backend.Entity.Hall;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NewMovieDTO {
    private String titel;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime movieDate;
    private Long hall;

//    public NewMovieDTO(String titel,LocalDateTime movieDate,Hall hall){
//        this.titel=titel;
//        this.movieDate = movieDate;
//        this.hall =hall;
//    }
}
