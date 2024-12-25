create database orderDB;

USE orderDB;


create table goods
(
    goods_id     bigint auto_increment
        primary key,
    goods_height double       null,
    goods_length double       null,
    goods_name   varchar(255) null,
    goods_type   varchar(255) null,
    goods_weight double       null,
    goods_width  double       null
);

create table orders
(
    order_id          bigint auto_increment
        primary key,
    goods_id          bigint       null,
    order_number      varchar(255) null,
    generation_date   date         null,
    finish_date       date         null,
    origin_place      varchar(255) null,
    destination_place varchar(255) null,
    price             double       null,
    deliver_status    int          null,
    payment_completed bit          null
);

