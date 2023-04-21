create table shelves(
    id         bigint auto_increment primary key,
    shelf_name varchar(255) not null,
    user_id    bigint       not null,
    constraint FK26j0prhaxbvf2c3mf9f2p5dck
        foreign key (user_id) references users (id)
);