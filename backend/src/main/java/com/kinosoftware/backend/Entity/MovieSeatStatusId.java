package com.kinosoftware.backend.Entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class MovieSeatStatusId implements Serializable {
    private Movie movieId;
    private Seats seatId;
}
