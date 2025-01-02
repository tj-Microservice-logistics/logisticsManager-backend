create database storageDB;

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
