

-- Drop table

-- DROP TABLE t_gateway;

CREATE TABLE t_gateway (
	id serial NOT NULL,
	"name" varchar(100) NOT NULL,
	description varchar(100) NULL,
	deleted boolean default false,
	do_project_id varchar(100) not null,
    do_api_token varchar(100) not null,
    do_app_spec jsonb,
    do_redis_spec jsonb,
    do_app_id varchar(100),
    do_db_id varchar(100),
    region jsonb NOT NULL,
    do_redis_status varchar(20),
    do_app_status varchar(20),
    container_id int,
    db_id int,
    no_of_container_instances int,
    db_standby boolean,
    status varchar(20),
	created_date timestamptz NOT NULL,
    last_updated_date timestamptz NOT NULL,
	CONSTRAINT pk_t_gateway_id PRIMARY KEY (id)

);
