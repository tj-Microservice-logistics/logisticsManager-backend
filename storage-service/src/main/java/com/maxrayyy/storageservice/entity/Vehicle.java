package com.maxrayyy.storageservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_id")
    private Integer vehicleId;

    @Column(name = "license_plate", nullable = false, unique = true)
    private String licensePlate;

    @Column(name = "vehicle_type", nullable = false)
    private String vehicleType; // e.g., truck, van, forklift, drone

    @Column(name = "status", nullable = false)
    private Boolean status; // 车辆状态：true 表示可用，false 表示不可用

    @Column(name = "warehouse_id")
    private Integer warehouseId;

    @Column(name = "driver_name")
    private String driverName;

    // 与 Warehouse 实体建立多对一关系
    @ManyToOne
    @JoinColumn(name = "warehouse", referencedColumnName = "warehouse_id")
    private Warehouse warehouse; // 关联 Warehouse 实体

}
