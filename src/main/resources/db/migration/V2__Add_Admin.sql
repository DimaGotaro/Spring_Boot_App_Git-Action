insert into usr (active, money, password, username) values (true, 1111.11, 'q', 'Admin')
ON CONFLICT (username) DO NOTHING;

insert into user_role (user_id, roles) values ((select id from usr where username = 'Admin'), 'ADMIN')
ON CONFLICT ON CONSTRAINT user_role_pk DO NOTHING;