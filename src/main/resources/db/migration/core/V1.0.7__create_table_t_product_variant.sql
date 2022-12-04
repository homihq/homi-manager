
CREATE TABLE t_product_variant (
      id int NOT NULL,
      description varchar(200) NOT NULL,
      slug varchar(50) ,
      deleted boolean default false,
      version int default 0,
      product_id int NOT NULL,
      price_amount_monthly int NOT NULL,
      price_currency_monthly varchar(10) NOT NULL,
      created_date timestamptz NOT NULL default now(),
      last_updated_date timestamptz NOT NULL default now(),
      CONSTRAINT pk_t_product_variant PRIMARY KEY (id)
);
-- t_product_variant foreign keys

ALTER TABLE t_product_variant ADD CONSTRAINT t_product_variant_fk_product_id FOREIGN KEY (product_id) REFERENCES t_product(id);


INSERT INTO t_product_variant (id, description, slug, product_id, price_amount_monthly, price_currency_monthly)
    VALUES (1, 'Basic/ 1 vCPU / 1 GB RAM / 10 GB SSD',  'db-s-1vcpu-1gb', 1, 15, 'USD'),
     (2, 'Basic/ 1 vCPU / 2 GB RAM / 25 GB SSD',  'db-s-1vcpu-2gb', 1, 30, 'USD'),
     (3, 'Basic/ 2 vCPU / 4 GB RAM / 38 GB SSD',  'db-s-2vcpu-4gb', 1, 60, 'USD'),
     (4, 'Basic/ 4 vCPU / 8 GB RAM / 115 GB SSD',  'db-s-4vcpu-8gb', 1, 120, 'USD');


INSERT INTO t_product_variant (id, description, slug, product_id, price_amount_monthly, price_currency_monthly)
    VALUES (5, '1 vCPU / 1 GB RAM',  'professional-xxs', 2, 12, 'USD'),
     (6, '1 vCPU / 2 GB RAM',  'professional-xs', 2, 25, 'USD'),
     (7, '2 vCPU / 4 GB RAM',  'professional-s', 2, 50, 'USD');
