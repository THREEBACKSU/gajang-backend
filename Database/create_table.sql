create database gajang;
use gajang;
create table bank (
    b_id int not null auto_increment,
    name varchar(32) not null,
    primary key(b_id)
);
create table address (
    a_id int not null auto_increment,
    province varchar(32) not null,
    city varchar(32) not null,
    town varchar(32) not null,
    primary key(a_id)
);
create table user (
    u_id varchar(32) not null,
    password varchar(80) not null,
    name varchar(32) not null,
    address_id int not null,
    phone varchar(32) not null,
    bank_id int not null,
    account_number varchar(64) not null,
    join_date datetime not null,
    role varchar(16) not null,
    rating float default 36.5,
    primary key(u_id),
    foreign key(address_id) references address(a_id),
    foreign key(bank_id) references bank(b_id)
);