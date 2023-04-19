CREATE OR REPLACE VIEW FIO_View AS SELECT concat(surname || ' ' || "name" || ' ' || second_surname)  as "ФИО" FROM human;
CREATE OR REPLACE VIEW TypeAndGroupResponsibility AS SELECT concat(g.name), tor.name AS "type" FROM responsibility_group g INNER JOIN type_of_responsibility tor on g.id = tor.group_id;
ALTER TABLE typeandgroupresponsibility RENAME COLUMN concat TO "group";
CREATE OR REPLACE VIEW driver_id_count AS SELECT count(drivers_license_number) AS driver_id_MAX FROM drivers_license;
CREATE OR REPLACE VIEW different_car_colors AS SELECT b.name AS brand, c.name  FROM vehicle v INNER JOIN brand b on b.id = v.brand_id INNER JOIN color c on c.id = v.color_id GROUP BY b.name, c.name;
--Тут пример процедуры, но так как они мне в курсовой не понадобятся то тут только эта.
CREATE OR REPLACE FUNCTION InsertColor() returns void
    language plpgsql
AS
    $$
    DECLARE
color text;
BEGIN
SELECT color FROM color;
Insert INTO color VALUES (color);
end;
$$;