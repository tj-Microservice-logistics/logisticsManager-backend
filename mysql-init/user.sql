create database userDB;

USE userDB;

create table users
(
    user_id       int auto_increment
        primary key,
    username      varchar(50)  not null,
    password_hash varchar(255) not null,
    constraint username
        unique (username)
);