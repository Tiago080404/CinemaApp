DO $$
DECLARE
r INT;
    s INT;
BEGIN
FOR r IN 1..15 LOOP
        FOR s IN 1..25 LOOP
            INSERT INTO seats (hall_id, row_num, seat_num)
            VALUES (1, r, s);
END LOOP;
END LOOP;
END $$;