ALTER TABLE movie
ALTER COLUMN movie_date TYPE TIMESTAMP
USING movie_date::timestamp;

UPDATE movie SET movie_date = '2025-11-28 16:30:00' WHERE movie_id = 1;