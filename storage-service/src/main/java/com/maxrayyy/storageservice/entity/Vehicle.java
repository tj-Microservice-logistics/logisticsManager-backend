package com.maxrayyy.storageservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private Integer vehicleId;

    @Column(nullable = false, unique = true)
    private String licensePlate;

    @Column(nullable = false)
    private String vehicleType; // e.g., truck, van, forklift, drone

    @Column(nullable = false)
    private String status; // available, in_use, maintenance, out_of_service, idle

    // 与 Driver 实体建立多对一关系
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to")
    private Driver assignedTo; // 关联 Driver 实体

    // 与 Warehouse 实体建立多对一关系
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    @JsonBackReference
    private Warehouse warehouse; // 关联 Warehouse 实体

    @Column(name = "warehouse_id", insertable = false, updatable = false)
    private Integer warehouseId; // 外键，关联到 Warehouse

    @Transient
    private String warehouseName; // 通过 Warehouse 实体获取的仓库名称
}
