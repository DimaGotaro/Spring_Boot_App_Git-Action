delete from usr_message;
delete from user_role;
delete from message;
delete from usr;

insert into usr (id, active, money, password, username) values
                                                            (1, true, 1111.11, 'g', 'Dima'),
                                                            (2, true, 1111.11, 'a', 'Goga');
insert into user_role (user_id, roles) values (1, 'ADMIN');

insert into user_role (user_id, roles) values (2, 'USER');

insert into message(id, text, tag, author_id) values
                                                  (1, 'first', 'my-tag', 1),
                                                  (2, 'second', 'more', 1),
                                                  (3, 'third', 'my-tag', 1),
                                                  (4, 'fourth', 'another', 2);
alter sequence message_id_seq restart with 5;