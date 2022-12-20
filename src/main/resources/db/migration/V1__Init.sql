create table IF NOT EXISTS usr (
                                   id int8 generated by default as identity,
                                   active boolean,
                                   money numeric(19, 2),
                                   password varchar(15) not null,
                                   username varchar(10) not null unique,
                                   primary key (id)
);
create table IF NOT EXISTS message (
                                       id int8 generated by default as identity,
                                       author_name varchar(255),
                                       filename varchar(255),
                                       tag varchar(20) not null,
                                       text varchar(255) not null,
                                       author_id int8,
                                       primary key (id),
                                       constraint FKqhhiq2fjqs0a1cgrg9bueu7ab
                                           foreign key (author_id)
                                               references usr
);
create table IF NOT EXISTS user_role (
                                         user_id int8 not null,
                                         roles varchar(255),
                                         constraint user_role_pk primary key (user_id, roles),
                                         constraint FKfpm8swft53ulq2hl11yplpr5
                                             foreign key (user_id)
                                                 references usr
);
create table IF NOT EXISTS usr_message (
                                           user_id int8 not null,
                                           message_id int8 not null unique,
                                           constraint FKcyuowgtqihio0hsvsfhew00a
                                               foreign key (message_id)
                                                   references message,
                                           constraint FKctjchxexlxgr4syxy03levrro
                                               foreign key (user_id)
                                                   references usr
);