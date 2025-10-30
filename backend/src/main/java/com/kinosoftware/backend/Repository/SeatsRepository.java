package com.kinosoftware.backend.Repository;

import com.kinosoftware.backend.Entity.Seats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SeatsRepository extends JpaRepository<Seats,Long> {

    @Query(value = "select * from seats where seats.row_num=?1 and seats.seat_num=?2",nativeQuery = true)
    Seats findbySeatAndRowNum(int rowNum,int seatNum);
}
