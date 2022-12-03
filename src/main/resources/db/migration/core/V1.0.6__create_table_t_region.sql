
-- Drop table

-- DROP TABLE t_region;

CREATE TABLE t_region (
	id int NOT NULL,
	datacenter varchar(100) NOT NULL,
	region varchar(100) NOT NULL,
	continent varchar(50) ,
    slug varchar(50) ,
	deleted boolean default false,
	cloud_id int NOT NULL,
	created_date timestamptz NOT NULL default now(),
    last_updated_date timestamptz NOT NULL default now(),
	CONSTRAINT pk_t_region PRIMARY KEY (id)
);

ALTER TABLE t_region ADD CONSTRAINT fk_t_region FOREIGN KEY (cloud_id) REFERENCES t_cloud(id);


INSERT INTO t_region (id, datacenter, region, slug, continent, cloud_id) VALUES (1, 'NYC1', 'New York City, United States', 'nyc1', 'North America', 1);
INSERT INTO t_region (id, datacenter, region, slug, continent, cloud_id) VALUES (2, 'NYC3', 'New York City, United States', 'nyc3', 'North America', 1);
INSERT INTO t_region (id, datacenter, region, slug, continent, cloud_id) VALUES (3, 'AMS3', 'Amsterdam, the Netherland', 'ams3', 'Europe', 1);
INSERT INTO t_region (id, datacenter, region, slug, continent, cloud_id) VALUES (4, 'SFO3', 'San Francisco, United States', 'sfo3', 'North America', 1);
INSERT INTO t_region (id, datacenter, region, slug, continent, cloud_id) VALUES (5, 'SGP1', 'Singapore', 'sgp1', 'North Asia', 1);
INSERT INTO t_region (id, datacenter, region, slug, continent, cloud_id) VALUES (6, 'LON1', 'London, United Kingdom', 'lon1', 'Europe', 1);
INSERT INTO t_region (id, datacenter, region, slug, continent, cloud_id) VALUES (7, 'FRA1', 'Frankfurt, Germany', 'fra1', 'Europe', 1);
INSERT INTO t_region (id, datacenter, region, slug, continent, cloud_id) VALUES (8, 'TOR1', 'Toronto, Canada', 'tor1', 'North America', 1);
INSERT INTO t_region (id, datacenter, region, slug, continent, cloud_id) VALUES (9, 'BLR1', 'Bangalore, India', 'blr1', 'Asia', 1);
INSERT INTO t_region (id, datacenter, region, slug, continent, cloud_id) VALUES (10, 'SYD1', 'Sydney, Australia	', 'syd1', 'Australia', 1);


