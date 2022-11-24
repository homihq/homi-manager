

-- Drop table

-- DROP TABLE t_gateway;

CREATE TABLE t_gateway (
	id serial NOT NULL,
	"name" varchar(100) NOT NULL,
	description varchar(100) NULL,
	deleted boolean default false,
	project_id int not null,
    cloud_provider jsonb,
    cloud_region jsonb,
    cloud_gateway_plan jsonb,
    digital_ocean_app_spec jsonb,
    digital_ocean_app_id varchar(100),
    status varchar(20),
	created_date timestamptz NOT NULL,
    last_updated_date timestamptz NOT NULL,
	CONSTRAINT pk_t_gateway_id PRIMARY KEY (id)

);
