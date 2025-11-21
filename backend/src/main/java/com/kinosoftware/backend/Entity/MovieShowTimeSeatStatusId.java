package com.kinosoftware.backend.Entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class MovieShowTimeSeatStatusId implements Serializable {
    private Seats seats;
    private MovieShowTime movieShowTime;
}
