create database transportDB;

USE transportDB;

create table warehouse
(
    warehouse_id   int auto_increment
        primary key,
    warehouse_name varchar(255) null
);

create table route
(
    route_id        int auto_increment
        primary key,
    end_warehouse   int    null,
    order_id        int    null,
    start_warehouse int    null,
    total_cost      int    null,
    cargo_weight    double null,
    constraint FK4auas875nqucqxrhksoi441ve
        foreign key (start_warehouse) references warehouse (warehouse_id),
    constraint FKnbyx0x3wdbrs7s28h5ttalika
        foreign key (end_warehouse) references warehouse (warehouse_id)
);

create table route_warehouses
(
    sequence            int null,
    route_id            int null,
    warehouse_id        int null,
    route_warehouses_id int auto_increment
        primary key,
    constraint FK7qoidmw92u3xvajx97frlbgo
        foreign key (warehouse_id) references warehouse (warehouse_id),
    constraint FKlq9sqwvksjrg2q7xmdewmciti
        foreign key (route_id) references route (route_id)
);

create table warehouse_distance
(
    warehouse_distance_id int auto_increment
        primary key,
    distance              double not null,
    warehouse_id_1        int    not null,
    warehouse_id_2        int    not null,
    cost                  int    null,
    constraint UKgm3i1215yaybmgv5iw7c9cj8o
        unique (warehouse_id_1, warehouse_id_2),
    constraint FKbr6dbsb3fkjb64dhcanvj9gvb
        foreign key (warehouse_id_2) references warehouse (warehouse_id),
    constraint FKcuw7i8pru9cykp60atd21vivo
        foreign key (warehouse_id_1) references warehouse (warehouse_id)
);

create table waybill
(
    waybill_id           int auto_increment
        primary key,
    created_at           datetime(6)  null,
    updated_at           datetime(6)  null,
    transport_status     varchar(255) null,
    vehicle_plate_number varchar(255) null,
    route_id             int          null,
    cargo_weight         double       null,
    end                  int          null,
    start                int          null,
    driver_name          varchar(255) null,
    order_id             int          null,
    constraint FKfhpd4td78jcnjbqit2gh3xsyu
        foreign key (route_id) references route (route_id),
    constraint FKjkb58r3ndqc5awiay5d566blh
        foreign key (end) references warehouse (warehouse_id),
    constraint FKo6d6mh9fef1h80i5pvhc98dyp
        foreign key (start) references warehouse (warehouse_id)
);


