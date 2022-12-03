
CREATE TABLE t_part (
      id int NOT NULL,
      "name" varchar(100) NOT NULL,
      description varchar(250),
      slug varchar(50) ,
      active boolean default true,
      created_date timestamptz NOT NULL default now(),
      last_updated_date timestamptz NOT NULL default now(),
      cloud_id int NOT NULL,
      CONSTRAINT pkt_cloud_service PRIMARY KEY (id)
);

ALTER TABLE t_part ADD CONSTRAINT fk_t_part FOREIGN KEY (cloud_id) REFERENCES t_cloud(id);


INSERT INTO t_part (id, "name", description, cloud_id, slug)
    VALUES (1, 'Redis',  'Basic/ 1vCPU / 1 GB RAM / 10 GB SSD', 1, 'db-s-1vcpu-1gb');

INSERT INTO t_part (id, "name", description, cloud_id, slug)
    VALUES (2, 'Redis',  'Basic/ 1vCPU / 2 GB RAM / 25 GB SSD', 1, 'db-s-1vcpu-2gb');

INSERT INTO t_part (id, "name", description, cloud_id, slug)
    VALUES (3, 'Container',  'Pro/ 1vCPU / 1 GB RAM', 1, 'professional-xxs');

INSERT INTO t_part (id, "name", description, cloud_id, slug)
    VALUES (4, 'Container',  'Pro/ 1vCPU / 2 GB RAM', 1, 'professional-xs');

INSERT INTO t_part (id, "name", description, cloud_id, slug)
    VALUES (5, 'Container',  'Pro/ 2vCPU / 4 GB RAM', 1, 'professional-s');

CREATE TABLE t_package (
      product_id int NOT NULL,
      part_id int NOT NULL,
      CONSTRAINT pk_t_package PRIMARY KEY (product_id, part_id)
);

ALTER TABLE t_package ADD CONSTRAINT fk_t_package_product FOREIGN KEY (product_id) REFERENCES t_product(id);
ALTER TABLE t_package ADD CONSTRAINT fk_t_package_part FOREIGN KEY (part_id) REFERENCES t_part(id);

