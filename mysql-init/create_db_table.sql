create database IF NOT EXISTS orderDB;
create database reportDB;
create database transportDB;
create database storageDB;
create database userDB;


USE reportDB;

create table IF NOT EXISTS order_raw_data
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

create table IF NOT EXISTS order_statistics
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

create table IF NOT EXISTS reports
(
    id          bigint auto_increment
        primary key,
    create_time datetime(6)  null,
    name        varchar(255) null,
    type        varchar(255) null,
    path        varchar(255) null
);

-- 插入1月份每天的统计数据
INSERT INTO order_statistics (stat_period, granularity, total_orders, total_amount) VALUES                                                                                        ('2024-01-01', 'DAILY', 3, 450.50),
                                                                                                                                                                                  ('2024-01-02', 'DAILY', 4, 520.75),
                                                                                                                                                                                  ('2024-01-03', 'DAILY', 2, 375.00),
                                                                                                                                                                                  ('2024-01-04', 'DAILY', 5, 680.25),
                                                                                                                                                                                  ('2024-01-05', 'DAILY', 3, 420.00),
                                                                                                                                                                                  ('2024-01-06', 'DAILY', 4, 550.00),
                                                                                                                                                                                  ('2024-01-07', 'DAILY', 3, 390.00),
                                                                                                                                                                                  ('2024-01-08', 'DAILY', 2, 245.00),
                                                                                                                                                                                  ('2024-01-09', 'DAILY', 4, 460.00),
                                                                                                                                                                                  ('2024-01-10', 'DAILY', 3, 380.00),
                                                                                                                                                                                  ('2024-01-11', 'DAILY', 5, 600.00),
                                                                                                                                                                                  ('2024-01-12', 'DAILY', 4, 500.00),
                                                                                                                                                                                  ('2024-01-13', 'DAILY', 3, 450.00),
                                                                                                                                                                                  ('2024-01-14', 'DAILY', 4, 600.00),
                                                                                                                                                                                  ('2024-01-15', 'DAILY', 3, 400.00),
                                                                                                                                                                                  ('2024-01-16', 'DAILY', 4, 550.00),
                                                                                                                                                                                  ('2024-01-17', 'DAILY', 3, 390.00),
                                                                                                                                                                                  ('2024-01-18', 'DAILY', 2, 245.00),
                                                                                                                                                                                  ('2024-01-19', 'DAILY', 4, 460.00),
                                                                                                                                                                                  ('2024-01-20', 'DAILY', 3, 380.00),
                                                                                                                                                                                  ('2024-01-21', 'DAILY', 4, 550.00),
                                                                                                                                                                                  ('2024-01-22', 'DAILY', 3, 400.00),
                                                                                                                                                                                  ('2024-01-23', 'DAILY', 4, 550.00),
                                                                                                                                                                                  ('2024-01-24', 'DAILY', 3, 500.00),
                                                                                                                                                                                  ('2024-01-25', 'DAILY', 2, 300.00),
                                                                                                                                                                                  ('2024-01-26', 'DAILY', 4, 550.00),
                                                                                                                                                                                  ('2024-01-27', 'DAILY', 3, 390.00),
                                                                                                                                                                                  ('2024-01-28', 'DAILY', 2, 245.00),
                                                                                                                                                                                  ('2024-01-29', 'DAILY', 4, 460.00),
                                                                                                                                                                                  ('2024-01-30', 'DAILY', 3, 380.00),
                                                                                                                                                                                  ('2024-01-31', 'DAILY', 5, 600.00);

-- 插入每月的统计数据
INSERT INTO order_statistics (stat_period, granularity, total_orders, total_amount) VALUES
                                                                                        ('2024-01', 'MONTHLY', 102, 13946.50),
                                                                                        ('2024-02', 'MONTHLY', 95, 12850.75),
                                                                                        ('2024-03', 'MONTHLY', 88, 11980.25),
                                                                                        ('2024-04', 'MONTHLY', 91, 12450.50),
                                                                                        ('2024-05', 'MONTHLY', 97, 13200.00),
                                                                                        ('2024-06', 'MONTHLY', 93, 12780.25),
                                                                                        ('2024-07', 'MONTHLY', 99, 13580.50),
                                                                                        ('2024-08', 'MONTHLY', 94, 12890.75),
                                                                                        ('2024-09', 'MONTHLY', 90, 12340.25),
                                                                                        ('2024-10', 'MONTHLY', 96, 13150.50),
                                                                                        ('2024-11', 'MONTHLY', 92, 12680.75),
                                                                                        ('2024-12', 'MONTHLY', 98, 13420.25);
INSERT INTO order_statistics (stat_period, granularity, total_orders, total_amount) VALUES
                                                                                        ('2023','YEARLY',1300,22300),
                                                                                        ('2024','YEARLY',1800,35000)
-- 插入更多数据到 order_raw_data 表
INSERT INTO order_raw_data (order_id, order_status, amount, order_create_time, order_update_time, create_time, raw_message) VALUES
-- 1月数据
(1001, 'COMPLETED', 150.00, '2024-01-01 10:00:00', '2024-01-01 10:05:00', '2024-01-01 10:10:00', 'Order processed successfully.'),
(1002, 'COMPLETED', 200.50, '2024-01-01 11:00:00', '2024-01-01 11:05:00', '2024-01-01 11:10:00', 'Order processed successfully.'),
(1003, 'CANCELLED', 75.00, '2024-01-01 12:00:00', '2024-01-01 12:05:00', '2024-01-01 12:10:00', 'Order was cancelled by user.'),
(1004, 'COMPLETED', 300.00, '2024-01-02 10:00:00', '2024-01-02 10:05:00', '2024-01-02 10:10:00', 'Order processed successfully.'),
(1005, 'PENDING', 120.75, '2024-01-02 11:00:00', '2024-01-02 11:05:00', '2024-01-02 11:10:00', 'Order is pending payment.'),
(1006, 'COMPLETED', 250.00, '2024-01-02 12:00:00', '2024-01-02 12:05:00', '2024-01-02 12:10:00', 'Order processed successfully.'),
(1007, 'COMPLETED', 180.00, '2024-01-02 13:00:00', '2024-01-02 13:05:00', '2024-01-02 13:10:00', 'Order processed successfully.'),

-- 2月数据
(2001, 'COMPLETED', 220.00, '2024-02-01 10:00:00', '2024-02-01 10:05:00', '2024-02-01 10:10:00', 'Order processed successfully.'),
(2002, 'PENDING', 175.50, '2024-02-01 11:00:00', '2024-02-01 11:05:00', '2024-02-01 11:10:00', 'Order is pending payment.'),
(2003, 'COMPLETED', 320.00, '2024-02-01 12:00:00', '2024-02-01 12:05:00', '2024-02-01 12:10:00', 'Order processed successfully.'),

-- 3月数据
(3001, 'COMPLETED', 280.00, '2024-03-01 10:00:00', '2024-03-01 10:05:00', '2024-03-01 10:10:00', 'Order processed successfully.'),
(3002, 'CANCELLED', 150.50, '2024-03-01 11:00:00', '2024-03-01 11:05:00', '2024-03-01 11:10:00', 'Order was cancelled by user.'),
(3003, 'COMPLETED', 420.00, '2024-03-01 12:00:00', '2024-03-01 12:05:00', '2024-03-01 12:10:00', 'Order processed successfully.'),

-- 4月数据
(4001, 'COMPLETED', 190.00, '2024-04-01 10:00:00', '2024-04-01 10:05:00', '2024-04-01 10:10:00', 'Order processed successfully.'),
(4002, 'PENDING', 225.50, '2024-04-01 11:00:00', '2024-04-01 11:05:00', '2024-04-01 11:10:00', 'Order is pending payment.'),
(4003, 'COMPLETED', 340.00, '2024-04-01 12:00:00', '2024-04-01 12:05:00', '2024-04-01 12:10:00', 'Order processed successfully.'),

-- 5月数据
(5001, 'COMPLETED', 230.00, '2024-05-01 10:00:00', '2024-05-01 10:05:00', '2024-05-01 10:10:00', 'Order processed successfully.'),
(5002, 'CANCELLED', 185.50, '2024-05-01 11:00:00', '2024-05-01 11:05:00', '2024-05-01 11:10:00', 'Order was cancelled by user.'),
(5003, 'COMPLETED', 360.00, '2024-05-01 12:00:00', '2024-05-01 12:05:00', '2024-05-01 12:10:00', 'Order processed successfully.'),

-- 6月数据
(6001, 'COMPLETED', 270.00, '2024-06-01 10:00:00', '2024-06-01 10:05:00', '2024-06-01 10:10:00', 'Order processed successfully.'),
(6002, 'PENDING', 195.50, '2024-06-01 11:00:00', '2024-06-01 11:05:00', '2024-06-01 11:10:00', 'Order is pending payment.'),
(6003, 'COMPLETED', 380.00, '2024-06-01 12:00:00', '2024-06-01 12:05:00', '2024-06-01 12:10:00', 'Order processed successfully.'),

-- 7月数据
(7001, 'COMPLETED', 240.00, '2024-07-01 10:00:00', '2024-07-01 10:05:00', '2024-07-01 10:10:00', 'Order processed successfully.'),
(7002, 'CANCELLED', 165.50, '2024-07-01 11:00:00', '2024-07-01 11:05:00', '2024-07-01 11:10:00', 'Order was cancelled by user.'),
(7003, 'COMPLETED', 400.00, '2024-07-01 12:00:00', '2024-07-01 12:05:00', '2024-07-01 12:10:00', 'Order processed successfully.'),

-- 8月数据
(8001, 'COMPLETED', 260.00, '2024-08-01 10:00:00', '2024-08-01 10:05:00', '2024-08-01 10:10:00', 'Order processed successfully.'),
(8002, 'PENDING', 205.50, '2024-08-01 11:00:00', '2024-08-01 11:05:00', '2024-08-01 11:10:00', 'Order is pending payment.'),
(8003, 'COMPLETED', 440.00, '2024-08-01 12:00:00', '2024-08-01 12:05:00', '2024-08-01 12:10:00', 'Order processed successfully.'),

-- 9月数据
(9001, 'COMPLETED', 290.00, '2024-09-01 10:00:00', '2024-09-01 10:05:00', '2024-09-01 10:10:00', 'Order processed successfully.'),
(9002, 'CANCELLED', 215.50, '2024-09-01 11:00:00', '2024-09-01 11:05:00', '2024-09-01 11:10:00', 'Order was cancelled by user.'),
(9003, 'COMPLETED', 460.00, '2024-09-01 12:00:00', '2024-09-01 12:05:00', '2024-09-01 12:10:00', 'Order processed successfully.'),

-- 10月数据
(10001, 'COMPLETED', 310.00, '2024-10-01 10:00:00', '2024-10-01 10:05:00', '2024-10-01 10:10:00', 'Order processed successfully.'),
(10002, 'PENDING', 225.50, '2024-10-01 11:00:00', '2024-10-01 11:05:00', '2024-10-01 11:10:00', 'Order is pending payment.'),
(10003, 'COMPLETED', 480.00, '2024-10-01 12:00:00', '2024-10-01 12:05:00', '2024-10-01 12:10:00', 'Order processed successfully.'),

-- 11月数据
(11001, 'COMPLETED', 330.00, '2024-11-01 10:00:00', '2024-11-01 10:05:00', '2024-11-01 10:10:00', 'Order processed successfully.'),
(11002, 'CANCELLED', 235.50, '2024-11-01 11:00:00', '2024-11-01 11:05:00', '2024-11-01 11:10:00', 'Order was cancelled by user.'),
(11003, 'COMPLETED', 500.00, '2024-11-01 12:00:00', '2024-11-01 12:05:00', '2024-11-01 12:10:00', 'Order processed successfully.'),

-- 12月数据
(12001, 'COMPLETED', 350.00, '2024-12-01 10:00:00', '2024-12-01 10:05:00', '2024-12-01 10:10:00', 'Order processed successfully.'),
(12002, 'PENDING', 245.50, '2024-12-01 11:00:00', '2024-12-01 11:05:00', '2024-12-01 11:10:00', 'Order is pending payment.'),
(12003, 'COMPLETED', 520.00, '2024-12-01 12:00:00', '2024-12-01 12:05:00', '2024-12-01 12:10:00', 'Order processed successfully.');

-- 继续插入1月份更多数据（每天多条记录）
INSERT INTO order_raw_data (order_id, order_status, amount, order_create_time, order_update_time, create_time, raw_message)
SELECT
        13000 + ROW_NUMBER() OVER (ORDER BY a.a),
        CASE FLOOR(RAND() * 3)
            WHEN 0 THEN 'COMPLETED'
            WHEN 1 THEN 'PENDING'
            ELSE 'CANCELLED'
            END,
        ROUND(100 + RAND() * 400, 2),
        DATE_ADD('2024-01-01', INTERVAL a.a DAY) + INTERVAL FLOOR(RAND() * 24) HOUR + INTERVAL FLOOR(RAND() * 60) MINUTE,
        DATE_ADD('2024-01-01', INTERVAL a.a DAY) + INTERVAL FLOOR(RAND() * 24) HOUR + INTERVAL FLOOR(RAND() * 60) MINUTE + INTERVAL 5 MINUTE,
        DATE_ADD('2024-01-01', INTERVAL a.a DAY) + INTERVAL FLOOR(RAND() * 24) HOUR + INTERVAL FLOOR(RAND() * 60) MINUTE + INTERVAL 10 MINUTE,
        CASE FLOOR(RAND() * 3)
            WHEN 0 THEN 'Order processed successfully.'
            WHEN 1 THEN 'Order is pending payment.'
            ELSE 'Order was cancelled by user.'
            END
FROM
    (SELECT 0 a UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION
     SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) a,
    (SELECT 0 a UNION SELECT 1 UNION SELECT 2 UNION SELECT 3) b
WHERE
        a.a < 31;



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
