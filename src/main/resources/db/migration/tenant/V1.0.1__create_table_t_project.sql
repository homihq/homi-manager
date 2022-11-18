

-- Drop table

-- DROP TABLE t_project;

CREATE TABLE t_project (
	id serial NOT NULL,
	"name" varchar(100) NOT NULL,
	description varchar(100) NULL,
	created_date timestamptz NOT NULL,
	last_modified_date timestamptz NOT NULL,
	CONSTRAINT pk_t_project_id PRIMARY KEY (id),
	CONSTRAINT uk_project_name UNIQUE (name)
);
