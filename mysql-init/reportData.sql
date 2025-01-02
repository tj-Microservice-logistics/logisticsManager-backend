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
