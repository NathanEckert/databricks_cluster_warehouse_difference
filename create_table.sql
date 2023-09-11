
CREATE SCHEMA test_schema_debug;


CREATE TABLE
  `test_schema_debug`.`difference_cluster_vs_warehouse`
(
  date DATE NOT NULL
);

INSERT INTO
  `test_schema_debug`.`difference_cluster_vs_warehouse`
VALUES
("2021-07-19"),
("2021-07-19"),
("2021-07-20"),
("2021-07-20"),
("2021-07-20"),
("2021-07-20"),
("2021-07-21"),
("2021-07-21"),
("2021-07-21"),
("2021-07-21");