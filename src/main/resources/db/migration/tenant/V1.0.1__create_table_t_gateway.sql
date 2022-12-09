

-- Drop table

-- DROP TABLE t_gateway;

CREATE TABLE t_gateway (
	id serial NOT NULL,
	"name" varchar(100) NOT NULL,
	description varchar(100) NULL,
	deleted boolean default false,
	gateway_key varchar(100) not null UNIQUE,
    status varchar(20),
	created_date timestamptz NOT NULL,
    last_updated_date timestamptz NOT NULL,
	CONSTRAINT pk_t_gateway_id PRIMARY KEY (id)

);
