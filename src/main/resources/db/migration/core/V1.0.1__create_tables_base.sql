

-- Drop table

-- DROP TABLE t_tenant;

CREATE TABLE t_tenant (
      id serial4 NOT NULL,
      tenant_id varchar(250) NOT NULL,
      "name" varchar(200) NOT NULL,
      created_date timestamptz NOT NULL,
      deleted bool NOT NULL DEFAULT false,
      CONSTRAINT pk_t_tenant_id PRIMARY KEY (id),
      CONSTRAINT uk_tenant_id UNIQUE (tenant_id)
);


-- public.t_role definition

-- Drop table

-- DROP TABLE t_role;

CREATE TABLE t_role (
	id serial NOT NULL,
	"name" varchar(30) NOT NULL,
	display varchar(100) NOT NULL,
	CONSTRAINT pk_t_role PRIMARY KEY (id)
);



-- Drop table

-- DROP TABLE t_user;

CREATE TABLE t_user (
        id serial4 NOT NULL,
        mobile varchar(20) NULL,
        email varchar(100) NOT NULL,
        last_updated_date timestamptz NOT NULL,
        deleted bool NOT NULL DEFAULT false,
        created_date timestamptz NOT NULL,
        last_login_date timestamptz NULL,
        role_id int4 NOT NULL,
        "password" varchar(100) NULL,
        first_name varchar(100) NOT NULL,
        last_name varchar(100) NOT NULL,
        email_verified bool NULL DEFAULT false,
        tenant_id int4 NOT NULL,
        verification_token varchar(64) NULL,
        verification_expiry_date timestamptz NULL,
        enabled bool NULL DEFAULT false,
        CONSTRAINT t_user_email_key UNIQUE (email),
        CONSTRAINT t_user_mobile_key UNIQUE (mobile),
        CONSTRAINT t_user_pkey PRIMARY KEY (id)
);


-- t_user foreign keys

-- ALTER TABLE t_user ADD CONSTRAINT t_user_fk_tentant_id FOREIGN KEY (id) REFERENCES t_tenant(id);



-- nirvanacore.t_lead definition

-- Drop table

-- DROP TABLE t_lead;

CREATE TABLE t_lead (
      id serial4 NOT NULL,
      email varchar(250) NOT NULL,
      first_name varchar(100),
      last_name varchar(100),
      designation varchar(100),
      created_date timestamptz NOT NULL,
      CONSTRAINT pk_t_lead_id PRIMARY KEY (id)
);
