
CREATE TABLE t_product (
            id int NOT NULL,
            "name" varchar(100) NOT NULL,
            deleted boolean default false,
            created_date timestamptz NOT NULL default now(),
            last_updated_date timestamptz NOT NULL default now(),
            CONSTRAINT pk_t_product PRIMARY KEY (id)
);


INSERT INTO t_product (id, "name")
    VALUES (1, 'Redis');

INSERT INTO t_product (id, "name")
    VALUES (2, 'Container');




