create table if not exists books
(
    id              serial primary key,
    title           varchar(200) not null,
    author          varchar(100) not null,
    description     varchar(500) not null,
    published_date  date not null
    );

insert into books (title, author, description, published_date) values ('Java', 'James Gosling', 'Java Programming', '2001-01-01');
insert into books (title, author, description, published_date) values ('Python', 'Guido van Rossum', 'Python Programming', '2010-05-01');
insert into books (title, author, description, published_date) values ('C++', 'Bjarne Stroustrup', 'C++ Programming', '2020-11-18');
