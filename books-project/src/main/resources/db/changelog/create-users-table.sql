create table users
(
    id        bigint auto_increment primary key,
    email     varchar(255) not null,
    password  varchar(255) not null,
    user_name varchar(255) not null
);