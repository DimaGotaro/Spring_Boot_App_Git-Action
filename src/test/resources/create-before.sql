delete from user_role;
delete from usr;

insert into usr (id, active, money, password, username) values
                                                        (1, true, 1111.11, 'g', 'Dima'),
                                                        (2, true, 1111.11, 'a', 'Goga');
insert into user_role (user_id, roles) values (1, 'ADMIN');
insert into user_role (user_id, roles) values (2, 'USER');