CREATE TABLE hall
(
    id            serial primary key,
    total_rows    int,
    seats_per_row int
);

create table seats
(
    seats_id serial primary key,
    hall_id  INT REFERENCES hall (id) ON DELETE CASCADE,
    row_num  int,
    seat_num int,
    status   seat_status default 'Available'
);

create table reservations
(
    reservation_id   SERIAL PRIMARY KEY,
    customer_name    TEXT,
    reservation_time TIMESTAMP DEFAULT NOW(),
    movie            int references movie (movie_id) on delete cascade
);

CREATE TABLE reservation_seats
(
    reservation_id INT REFERENCES reservations (reservation_id) ON DELETE CASCADE,
    seat_id        INT REFERENCES seats (seats_id) ON DELETE CASCADE,
    PRIMARY KEY (reservation_id, seat_id)
);

create table movie
(
    movie_id   serial primary key,
    titel      varchar(255),
    movie_date Date,
    hall       int references hall (id) on delete cascade
);

create type seat_status as enum(
'Available',
'Booked'
);

insert into hall(total_rows,seats_per_row) values(15,25);
insert into hall(total_rows,seats_per_row) values(20,25);
insert into hall(total_rows,seats_per_row) values(10,15);


INSERT INTO movie (titel, movie_date, hall) VALUES('Demon Slayer', '2025-11-01', 1);