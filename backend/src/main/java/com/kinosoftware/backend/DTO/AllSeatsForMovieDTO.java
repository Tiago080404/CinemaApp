package com.kinosoftware.backend.DTO;

import com.kinosoftware.backend.Entity.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllSeatsForMovieDTO {

    private Integer movieId;
    private String status;
    private Integer seatsId;
    private Integer hallId;
    private Integer rowNum;
    private Integer seatNum;

    public AllSeatsForMovieDTO(Integer movieId, String status, Integer seatsId, Integer hallId, Integer rowNum, Integer seatNum) {
        this.movieId = movieId;
        this.status = status;
        this.seatsId = seatsId;
        this.hallId = hallId;
        this.rowNum = rowNum;
        this.seatNum = seatNum;
    }
}