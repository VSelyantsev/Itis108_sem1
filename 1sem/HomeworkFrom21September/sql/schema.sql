drop table if exists blockchain;
drop table if exists address;
drop table if exists block;
drop table if exists transaction;

create table block (
    id serial primary key,
    number int not null,
    time date,
    size float
);

create table address (
    id serial primary key ,
    hash_adr int generated always as identity primary key ,
    balance int generated always as identity primary key
);

create table transaction (
    id serial primary key,
    hash_tr varchar(255),
    status varchar(255),
    id_block int,
    time_tr date,
    id_from int,
    id_to int,
    value_tr float,
    fee float,
    FOREIGN KEY (id_block) REFERENCES block (id),
    FOREIGN KEY (id_from) REFERENCES address (hash_adr),
    FOREIGN KEY (id_to) REFERENCES address (balance)
);



create table blockchain (
    id serial primary key ,
    name varchar(255),
    id_address_main_coin int,
    FOREIGN KEY (id_address_main_coin) REFERENCES address (id)
);

