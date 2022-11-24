-- Drop table

-- DROP TABLE t_cloud_provider;

CREATE TABLE t_cloud_provider (
        id int NOT NULL,
        "name" varchar(100) NOT NULL,
        active boolean default false,
        created_date timestamptz NOT NULL default now(),
        last_updated_date timestamptz NOT NULL default now(),
        serial_no smallint,
        CONSTRAINT pk_t_cloud_provider PRIMARY KEY (id)
);

INSERT INTO t_cloud_provider (id, "name", active, serial_no) VALUES (1, 'DigitalOcean', true, 1);
INSERT INTO t_cloud_provider (id, "name", active, serial_no) VALUES (2, 'Render', false, 2);
INSERT INTO t_cloud_provider (id, "name", active, serial_no) VALUES (3, 'AWS', false, 3);


-- Drop table

-- DROP TABLE t_cloud_region;

CREATE TABLE t_cloud_region (
	id int NOT NULL,
	"name" varchar(100) NOT NULL,
	val varchar(20) NOT NULL,
	continent varchar(100) ,
	deleted boolean default false,
	created_date timestamptz NOT NULL default now(),
    last_updated_date timestamptz NOT NULL default now(),
    cloud_provider_id int NOT NULL,
	CONSTRAINT pk_t_cloud_region PRIMARY KEY (id)
);

ALTER TABLE t_cloud_region ADD CONSTRAINT fk_t_cloud_region FOREIGN KEY (cloud_provider_id) REFERENCES t_cloud_provider(id);


INSERT INTO t_cloud_region (id, "name", val, continent, cloud_provider_id) VALUES (1, 'New York', 'nyc', 'North America', 1);
INSERT INTO t_cloud_region (id, "name", val, continent, cloud_provider_id) VALUES (2, 'San Fransico', 'sfo', 'North America', 1);
INSERT INTO t_cloud_region (id, "name", val, continent, cloud_provider_id) VALUES (3, 'Toronto', 'tor', 'North America', 1);
INSERT INTO t_cloud_region (id, "name", val, continent, cloud_provider_id) VALUES (4, 'Amsterdam', 'ams', 'Europe', 1);
INSERT INTO t_cloud_region (id, "name", val, continent, cloud_provider_id) VALUES (5, 'Frankfurt', 'fra', 'Europe', 1);
INSERT INTO t_cloud_region (id, "name", val, continent, cloud_provider_id) VALUES (6, 'London', 'lon', 'Europe', 1);
INSERT INTO t_cloud_region (id, "name", val, continent, cloud_provider_id) VALUES (7, 'Singapore', 'sgp', 'Asia', 1);
INSERT INTO t_cloud_region (id, "name", val, continent, cloud_provider_id) VALUES (8, 'Bangalore', 'blr', 'Asia', 1);
INSERT INTO t_cloud_region (id, "name", val, continent, cloud_provider_id) VALUES (9, 'Sydney', 'syd', 'Australia', 1);


CREATE TABLE t_cloud_gateway_plan (
            id int NOT NULL,
            "name" varchar(100) NOT NULL,
            features jsonb NULL,
            price int default 0,
            currency_code varchar(10),
            currency_symbol varchar(2),
            currency_name varchar(50),
            deleted boolean default false,
            created_date timestamptz NOT NULL default now(),
            last_updated_date timestamptz NOT NULL default now(),
            cloud_provider_id int NOT NULL,
            CONSTRAINT pk_t_cloud_gateway_plan PRIMARY KEY (id)
);

ALTER TABLE t_cloud_gateway_plan ADD CONSTRAINT fk_t_cloud_gateway_plan FOREIGN KEY (cloud_provider_id) REFERENCES t_cloud_provider(id);

INSERT INTO t_cloud_gateway_plan (id, "name", price, currency_code, currency_symbol, currency_name, cloud_provider_id) VALUES (1, 'Dev', 100, 'USD', '$', 'United States Dollar', 1);
INSERT INTO t_cloud_gateway_plan (id, "name", price, currency_code, currency_symbol, currency_name, cloud_provider_id) VALUES (2, 'Stage', 250, 'USD', '$', 'United States Dollar', 1);
INSERT INTO t_cloud_gateway_plan (id, "name", price, currency_code, currency_symbol, currency_name, cloud_provider_id) VALUES (3, 'Production', 500, 'USD', '$', 'United States Dollar', 1);
INSERT INTO t_cloud_gateway_plan (id, "name", price, currency_code, currency_symbol, currency_name, cloud_provider_id) VALUES (4, 'Enterprise', 0, 'USD', '$', 'United States Dollar', 1);
