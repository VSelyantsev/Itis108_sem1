drop table if exists account;

create table account (
    id serial primary key,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    login_name varchar(255) not null,
    password_account varchar(255) not null,
    email varchar(255) not null
);

create table history (
    id serial primary key,
    username varchar(255) not null,
    city varchar(255) not null,
    vremya varchar(255) not null
);