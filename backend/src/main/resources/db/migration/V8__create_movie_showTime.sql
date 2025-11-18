ALTER table movie drop column hall cascade;



create table movie_showTime(
    id serial primary key ,
    movie_id int references movie (movie_id) on delete cascade,
    hall int references hall (id) on delete cascade,
    show_date DATE,
    show_time TIME
);