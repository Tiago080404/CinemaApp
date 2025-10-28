package com.kinosoftware.backend.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetMovieSeatsResponseDTO {
    private String titel;
    private int row_num;
    private int seat_num;

    public GetMovieSeatsResponseDTO(String titel, int row_num, int seat_num) {
        this.titel = titel;
        this.row_num = row_num;
        this.seat_num = seat_num;
    }
}
