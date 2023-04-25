CREATE OR REPLACE VIEW FIO_View AS SELECT concat(surname || ' ' || "name" || ' ' || second_surname)  as "ФИО" FROM human;
CREATE OR REPLACE VIEW TypeAndGroupResponsibility AS SELECT concat(g.name), tor.name AS "type" FROM responsibility_group g INNER JOIN type_of_responsibility tor on g.id = tor.group_id;
ALTER TABLE typeandgroupresponsibility RENAME COLUMN concat TO "group";
CREATE OR REPLACE VIEW driver_id_count AS SELECT count(drivers_license_number) AS driver_id_MAX FROM drivers_license;
CREATE OR REPLACE VIEW different_car_colors AS SELECT b.name AS brand, c.name  FROM vehicle v INNER JOIN brand b on b.id = v.brand_id INNER JOIN color c on c.id = v.color_id GROUP BY b.name, c.name;
--Тут пример процедуры
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

CREATE OR REPLACE FUNCTION SelectDecree() returns void
    language plpgsql
AS
$$
BEGIN
SELECT resolution_number,
       dln_id,
       v.name,
       v2.state_number,
       r.name,
       s.name,
       tor.name,
       rg.name
FROM decree
         Inner Join violation v on v.id = decree.violation_id
         Inner Join vehicle v2 on v2.id = decree.vehicle_id
         INNER JOIN region r on r.id = decree.region_id
         INNER JOIN status s on s.id = decree.status_id
         Inner join type_of_responsibility tor on decree.responsibility_id = tor.id
         Inner Join responsibility_group rg on tor.group_id = rg.id;
end;
$$;

CREATE OR REPLACE FUNCTION SelectDecree() returns void
    language plpgsql
AS
$$
BEGIN
SELECT state_number, win, c.name, b.name
FROM vehicle
         INNER JOIN color c on c.id = vehicle.color_id
         INNER JOIN brand b on b.id = vehicle.brand_id
end;
$$;
