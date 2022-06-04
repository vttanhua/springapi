-- USER
-- non-encrypted password: letmein
INSERT INTO security_client (id, clientid, clientsecret, name) VALUES
(1,  'admin', '$2a$12$ZhGS.zcWt1gnZ9xRNp7inOvo5hIT0ngN7N.pN939cShxKvaQYHnnu', 'Administrator'),
(2,  'csr_vtt', '$2a$12$ZhGS.zcWt1gnZ9xRNp7inOvo5hIT0ngN7N.pN939cShxKvaQYHnnu', 'Vesa T-T');

-- ROLES
INSERT INTO security_role (id, role_name, description) VALUES (1, 'ROLE_ADMIN', 'Administrator');
INSERT INTO security_role (id, role_name, description) VALUES (2, 'ROLE_CSR', 'Customer Service Representative');

-- CLIENT ROLES
INSERT INTO client_role(client_id, role_id) VALUES
 (1, 1), -- give admin ROLE_ADMIN
 (2, 2);  -- give vtt ROLE_CSR
