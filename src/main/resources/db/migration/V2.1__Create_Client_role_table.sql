create table client_role (
client_id bigint not null, 
role_id bigint not null
);

CREATE unique INDEX IF NOT EXISTS client_id_role_id_indx on client_role (client_id,role_id);
