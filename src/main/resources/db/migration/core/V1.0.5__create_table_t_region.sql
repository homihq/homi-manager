
-- Drop table

-- DROP TABLE t_region;

CREATE TABLE t_region (
	id int NOT NULL,
	datacenter varchar(100) NOT NULL,
	region varchar(100) NOT NULL,
	continent varchar(50) ,
    slug varchar(50) ,
	deleted boolean default false,
	created_date timestamptz NOT NULL default now(),
    last_updated_date timestamptz NOT NULL default now(),
	CONSTRAINT pk_t_region PRIMARY KEY (id)
);


INSERT INTO t_region (id, datacenter, region, slug, continent) VALUES (1, 'NYC1', 'New York City, United States', 'nyc1', 'North America');
INSERT INTO t_region (id, datacenter, region, slug, continent) VALUES (2, 'NYC3', 'New York City, United States', 'nyc3', 'North America');
INSERT INTO t_region (id, datacenter, region, slug, continent) VALUES (3, 'AMS3', 'Amsterdam, The Netherland', 'ams3', 'Europe');
INSERT INTO t_region (id, datacenter, region, slug, continent) VALUES (4, 'SFO3', 'San Francisco, United States', 'sfo3', 'North America');
INSERT INTO t_region (id, datacenter, region, slug, continent) VALUES (5, 'SGP1', 'Singapore', 'sgp1', 'Asia');
INSERT INTO t_region (id, datacenter, region, slug, continent) VALUES (6, 'LON1', 'London, United Kingdom', 'lon1', 'Europe');
INSERT INTO t_region (id, datacenter, region, slug, continent) VALUES (7, 'FRA1', 'Frankfurt, Germany', 'fra1', 'Europe');
INSERT INTO t_region (id, datacenter, region, slug, continent) VALUES (8, 'TOR1', 'Toronto, Canada', 'tor1', 'North America');
INSERT INTO t_region (id, datacenter, region, slug, continent) VALUES (9, 'BLR1', 'Bangalore, India', 'blr1', 'Asia');
INSERT INTO t_region (id, datacenter, region, slug, continent) VALUES (10, 'SYD1', 'Sydney, Australia	', 'syd1', 'Australia');


