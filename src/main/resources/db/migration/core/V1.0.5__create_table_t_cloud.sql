-- Drop table

-- DROP TABLE t_cloud;

CREATE TABLE t_cloud (
        id int NOT NULL,
        "name" varchar(100) NOT NULL,
        active boolean default false,
        created_date timestamptz NOT NULL default now(),
        last_updated_date timestamptz NOT NULL default now(),
        serial_no smallint,
        version smallint,
        CONSTRAINT pk_t_cloud_provider PRIMARY KEY (id)
);

INSERT INTO t_cloud (id, "name", active, serial_no) VALUES (1, 'DigitalOcean', true, 1);
INSERT INTO t_cloud (id, "name", active, serial_no) VALUES (2, 'Render', false, 2);
INSERT INTO t_cloud (id, "name", active, serial_no) VALUES (3, 'AWS', false, 3);

