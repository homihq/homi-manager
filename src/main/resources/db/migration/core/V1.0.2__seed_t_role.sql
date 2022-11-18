INSERT INTO t_role (id,"name", display) VALUES (1,'ROLE_SUPER_ADMIN','Super Admin') ON CONFLICT (id) DO NOTHING;
INSERT INTO t_role (id,"name", display) VALUES (2,'ROLE_TENANT_ADMIN', 'Admin') ON CONFLICT (id) DO NOTHING;
INSERT INTO t_role (id,"name", display) VALUES (3,'ROLE_PROJECT_ADMIN', 'Project Admin') ON CONFLICT (id) DO NOTHING;
INSERT INTO t_role (id,"name", display) VALUES (4,'ROLE_PROJECT_USER', 'Project User') ON CONFLICT (id) DO NOTHING;

