create table shelved_books
(
    id        bigint auto_increment
        primary key,
    add_date  date   not null,
    read_data date   null,
    shelf_id  bigint null,
    book_id   bigint null,
    constraint FKepndglie4iqk05k6imhmwchiu
        foreign key (book_id) references books (id),
    constraint FKi4xdbewjp4ui8f5fhgbl1esmd
        foreign key (shelf_id) references shelves (id)
);