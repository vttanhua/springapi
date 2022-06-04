alter table client_role add constraint FKflexlfw1d2jyvxvti7xx3qp12 foreign key (role_id) references security_role;

alter table client_role add constraint FK2w1qs4cilvj1ef0xjwmbksnbu foreign key (client_id) references security_client;
