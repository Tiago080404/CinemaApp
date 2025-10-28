package com.kinosoftware.backend.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "hall")
@Getter
@Setter
@NoArgsConstructor
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long hallId;

    @Column(name = "total_rows")
    private int totalRows;

    @Column(name = "seats_per_row")
    private int seatsPerRow;
}
