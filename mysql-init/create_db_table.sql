create database reportDB;

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



create database object_management;

USE object_management;

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
        foreign key (assigned_to) references user_management.users (user_id),
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

create database user_management;

USE user_management;

create table users
(
    user_id       int auto_increment
        primary key,
    username      varchar(50)                                not null,
    password_hash varchar(255)                               not null,
    role          enum ('main_manager', 'warehouse_manager') not null,
    constraint username
        unique (username)
);

