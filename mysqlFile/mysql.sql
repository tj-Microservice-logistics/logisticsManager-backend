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

