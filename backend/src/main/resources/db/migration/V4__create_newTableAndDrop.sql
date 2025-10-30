CREATE TABLE movie_seat_status
(
    movie_id INT REFERENCES movie (movie_id) ON DELETE CASCADE,
    seat_id  INT REFERENCES seats (seats_id) ON DELETE CASCADE,
    status   seat_status DEFAULT 'Available',
    PRIMARY KEY (movie_id, seat_id)
);
ALTER TABLE seats
DROP COLUMN status;