-- Drop table

-- DROP TABLE t_billing_currency;

CREATE TABLE t_billing_currency (
    currency_code varchar(10),
    currency_symbol varchar(2),
    currency_name varchar(50),
    active boolean default true,
    created_date timestamptz NOT NULL default now(),
    last_updated_date timestamptz NOT NULL default now(),
    serial_no smallint default 0,
    CONSTRAINT pk_t_billing_currency PRIMARY KEY (currency_code)
);

INSERT INTO t_billing_currency (currency_code, currency_symbol, currency_name)
    VALUES ('USD', '$', 'United States Dollar');


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
	datacenter varchar(100) NOT NULL,
	region varchar(100) NOT NULL,
	continent varchar(50) ,
    slug varchar(50) ,
	deleted boolean default false,
	cloud_provider_id int NOT NULL,
	created_date timestamptz NOT NULL default now(),
    last_updated_date timestamptz NOT NULL default now(),
	CONSTRAINT pk_t_cloud_region PRIMARY KEY (id)
);

ALTER TABLE t_cloud_region ADD CONSTRAINT fk_t_cloud_region FOREIGN KEY (cloud_provider_id) REFERENCES t_cloud_provider(id);


INSERT INTO t_cloud_region (id, datacenter, region, slug, continent, cloud_provider_id) VALUES (1, 'NYC1', 'New York City, United States', 'nyc1', 'North America', 1);
INSERT INTO t_cloud_region (id, datacenter, region, slug, continent, cloud_provider_id) VALUES (2, 'NYC3', 'New York City, United States', 'nyc3', 'North America', 1);
INSERT INTO t_cloud_region (id, datacenter, region, slug, continent, cloud_provider_id) VALUES (3, 'AMS3', 'Amsterdam, the Netherland', 'ams3', 'Europe', 1);
INSERT INTO t_cloud_region (id, datacenter, region, slug, continent, cloud_provider_id) VALUES (4, 'SFO3', 'San Francisco, United States', 'sfo3', 'North America', 1);
INSERT INTO t_cloud_region (id, datacenter, region, slug, continent, cloud_provider_id) VALUES (5, 'SGP1', 'Singapore', 'sgp1', 'North Asia', 1);
INSERT INTO t_cloud_region (id, datacenter, region, slug, continent, cloud_provider_id) VALUES (6, 'LON1', 'London, United Kingdom', 'lon1', 'Europe', 1);
INSERT INTO t_cloud_region (id, datacenter, region, slug, continent, cloud_provider_id) VALUES (7, 'FRA1', 'Frankfurt, Germany', 'fra1', 'Europe', 1);
INSERT INTO t_cloud_region (id, datacenter, region, slug, continent, cloud_provider_id) VALUES (8, 'TOR1', 'Toronto, Canada', 'tor1', 'North America', 1);
INSERT INTO t_cloud_region (id, datacenter, region, slug, continent, cloud_provider_id) VALUES (9, 'BLR1', 'Bangalore, India', 'blr1', 'Asia', 1);
INSERT INTO t_cloud_region (id, datacenter, region, slug, continent, cloud_provider_id) VALUES (10, 'SYD1', 'Sydney, Australia	', 'syd1', 'Australia', 1);


CREATE TABLE t_cloud_gateway_plan (
            id int NOT NULL,
            "name" varchar(100) NOT NULL,
            price_monthly int default 0,
            currency_code varchar(10) NOT NULL,
            active boolean default true,
            created_date timestamptz NOT NULL default now(),
            last_updated_date timestamptz NOT NULL default now(),
            cloud_provider_id int NOT NULL,
            description varchar(250),
            CONSTRAINT pk_t_cloud_gateway_plan PRIMARY KEY (id)
);

ALTER TABLE t_cloud_gateway_plan ADD CONSTRAINT fk_t_cloud_gateway_plan FOREIGN KEY (cloud_provider_id) REFERENCES t_cloud_provider(id);

INSERT INTO t_cloud_gateway_plan (id, "name", price_monthly, currency_code, cloud_provider_id )
    VALUES (1, 'Essentials', 100, 'USD', 1 );

INSERT INTO t_cloud_gateway_plan (id, "name", price_monthly, currency_code, cloud_provider_id )
    VALUES (2, 'Standard', 250, 'USD', 1);

INSERT INTO t_cloud_gateway_plan (id, "name", price_monthly, currency_code, cloud_provider_id )
    VALUES (3, 'Premium', 500, 'USD', 1);

INSERT INTO t_cloud_gateway_plan (id, "name", price_monthly, currency_code, cloud_provider_id, active)
    VALUES (4, 'Custom', 0, 'USD', 1, false);


CREATE TABLE t_cloud_service (
      id int NOT NULL,
      "name" varchar(100) NOT NULL,
      description varchar(250),
      slug varchar(50) ,
      active boolean default true,
      created_date timestamptz NOT NULL default now(),
      last_updated_date timestamptz NOT NULL default now(),
      cloud_provider_id int NOT NULL,
      CONSTRAINT pkt_cloud_service PRIMARY KEY (id)
);

ALTER TABLE t_cloud_service ADD CONSTRAINT fk_t_cloud_service FOREIGN KEY (cloud_provider_id) REFERENCES t_cloud_provider(id);


INSERT INTO t_cloud_service (id, "name", description, cloud_provider_id, slug)
    VALUES (1, 'Redis',  'Basic/ 1vCPU / 1 GB RAM / 10 GB SSD * 1', 1, 'db-s-1vcpu-1gb');

INSERT INTO t_cloud_service (id, "name", description, cloud_provider_id, slug)
    VALUES (2, 'Redis',  'Basic/ 1vCPU / 2 GB RAM / 25 GB SSD * 1', 1, 'db-s-1vcpu-2gb');

INSERT INTO t_cloud_service (id, "name", description, cloud_provider_id, slug)
    VALUES (3, 'Container',  'Pro/ 1vCPU / 1 GB RAM * 1', 1, 'professional-xxs');

INSERT INTO t_cloud_service (id, "name", description, cloud_provider_id, slug)
    VALUES (4, 'Container',  'Pro/ 1vCPU / 2 GB RAM * 2', 1, 'professional-xs');

INSERT INTO t_cloud_service (id, "name", description, cloud_provider_id, slug)
    VALUES (5, 'Container',  'Pro/ 2vCPU / 4 GB RAM * 3', 1, 'professional-s');

CREATE TABLE t_cloud_gateway_plan_service (
      plan_id int NOT NULL,
      service_id int NOT NULL,
      CONSTRAINT pk_t_cloud_gateway_plan_service PRIMARY KEY (plan_id, service_id)
);

ALTER TABLE t_cloud_gateway_plan_service ADD CONSTRAINT fk_t_cloud_gateway_plan_service_plan FOREIGN KEY (plan_id) REFERENCES t_cloud_gateway_plan(id);
ALTER TABLE t_cloud_gateway_plan_service ADD CONSTRAINT fk_t_cloud_gateway_plan_service_srvc FOREIGN KEY (service_id) REFERENCES t_cloud_service(id);

