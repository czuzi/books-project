create table books
(
    id              bigint auto_increment primary key,
    author          varchar(255) not null,
    genre           varchar(255) not null,
    isbn            varchar(255) not null,
    number_of_pages int          not null,
    title           varchar(255) not null,
    year_of_publish int          not null
);