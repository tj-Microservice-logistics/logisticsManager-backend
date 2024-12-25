create database orderDB;
create database reportDB;
create database transportDB;
create database storageDB;
create database userDB;


USE reportDB;

create table order_raw_data
(
    id                bigint auto_increment comment '主键ID'
        primary key,
    order_id          bigint         not null comment '订单ID',
    order_status      varchar(50)    not null comment '订单状态',
    amount            decimal(10, 2) not null comment '订单金额',
    order_create_time datetime       not null comment '订单创建时间',
    order_update_time datetime       not null comment '订单更新时间',
    create_time       datetime       null,
    raw_message       varchar(500)   null
)
    comment '订单原始数据表';

create index idx_create_time
    on order_raw_data (create_time);

create index idx_order_create_time
    on order_raw_data (order_create_time);

create index idx_order_id
    on order_raw_data (order_id);

create table order_statistics
(
    id           bigint auto_increment comment '主键ID'
        primary key,
    stat_period  varchar(10)                 not null comment '统计周期(如: 2024-01-01)',
    granularity  varchar(10)                 not null comment '统计粒度(DAILY/MONTHLY/YEARLY)',
    total_orders int            default 0    not null comment '订单数量',
    total_amount decimal(15, 2) default 0.00 not null comment '订单金额总计',
    constraint uk_period_granularity
        unique (stat_period, granularity)
)
    comment '订单统计表';

create index idx_granularity_period
    on order_statistics (granularity, stat_period);

create table reports
(
    id          bigint auto_increment
        primary key,
    create_time datetime(6)  null,
    name        varchar(255) null,
    type        varchar(255) null,
    path        varchar(255) null
);




USE storageDB;

create table warehouses
(
    warehouse_id   int auto_increment
        primary key,
    warehouse_name varchar(100) not null,
    capacity       int          null
);

create table drivers
(
    driver_id        int auto_increment
        primary key,
    full_name        varchar(100)         not null,
    contact_number   varchar(15)          null,
    assigned_vehicle int                  null,
    warehouse_id     int                  null,
    is_available     tinyint(1) default 1 null,
    constraint drivers_ibfk_2
        foreign key (warehouse_id) references warehouses (warehouse_id)
);

create index assigned_vehicle
    on drivers (assigned_vehicle);

create index warehouse_id
    on drivers (warehouse_id);

create table vehicles
(
    vehicle_id    int auto_increment
        primary key,
    vehicle_type  enum ('truck', 'van', 'forklift', 'drone')                    not null,
    license_plate varchar(50)                                                   null,
    status        enum ('available', 'in_use', 'maintenance', 'out_of_service') not null,
    assigned_to   int                                                           null,
    warehouse_id  int                                                           null,
    constraint vehicles_ibfk_1
        foreign key (assigned_to) references userDB.users (user_id),
    constraint FKtl6jxmu3w19yoont80vnnxx8k
        foreign key (assigned_to) references drivers (driver_id),
    constraint vehicles_ibfk_2
        foreign key (warehouse_id) references warehouses (warehouse_id)
);

alter table drivers
    add constraint drivers_ibfk_1
        foreign key (assigned_vehicle) references vehicles (vehicle_id);

create index warehouse_id
    on vehicles (warehouse_id);

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
