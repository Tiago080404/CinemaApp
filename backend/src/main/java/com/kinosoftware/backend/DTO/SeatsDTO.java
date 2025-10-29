package com.kinosoftware.backend.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatsDTO {
    private int row_num;
    private int seat_num;

    public SeatsDTO(int row_num, int seat_num) {
        this.row_num = row_num;
        this.seat_num = seat_num;
    }
}
