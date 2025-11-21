drop table movie_seat_status;
create table movie_showtime_seat_status(
    showtime_id INT REFERENCES movie_showtime(id),
    seat_id INT REFERENCES seats(seats_id),
    status seat_status DEFAULT 'Available',
    PRIMARY KEY (showtime_id,seat_id)
);

ALTER TABLE reservations
DROP COLUMN movie,
ADD COLUMN showtime_id INT REFERENCES movie_showtime (id) ON DELETE CASCADE;