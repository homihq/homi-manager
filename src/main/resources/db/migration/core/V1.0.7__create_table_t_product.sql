
CREATE TABLE t_product (
            id int NOT NULL,
            "name" varchar(100) NOT NULL,
            price_monthly int default 0,
            currency_code varchar(10) NOT NULL,
            active boolean default true,
            created_date timestamptz NOT NULL default now(),
            last_updated_date timestamptz NOT NULL default now(),
            cloud_id int NOT NULL,
            description varchar(250),
            CONSTRAINT pk_t_cloud_gateway_plan PRIMARY KEY (id)
);

ALTER TABLE t_product ADD CONSTRAINT fk_t_product FOREIGN KEY (cloud_id) REFERENCES t_cloud(id);

INSERT INTO t_product (id, "name", price_monthly, currency_code, cloud_id )
    VALUES (1, 'Essentials', 100, 'USD', 1 );

INSERT INTO t_product (id, "name", price_monthly, currency_code, cloud_id )
    VALUES (2, 'Standard', 250, 'USD', 1);

INSERT INTO t_product (id, "name", price_monthly, currency_code, cloud_id )
    VALUES (3, 'Premium', 500, 'USD', 1);

INSERT INTO t_product (id, "name", price_monthly, currency_code, cloud_id, active)
    VALUES (4, 'Custom', 0, 'USD', 1, false);



