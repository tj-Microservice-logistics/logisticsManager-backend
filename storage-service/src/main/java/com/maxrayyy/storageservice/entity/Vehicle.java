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
    private Boolean status; // 车辆状态：true 表示可用，false 表示不可用

    // 与 Driver 实体建立多对一关系
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to")
    private Driver assignedTo; // 关联 Driver 实体

    // 与 Warehouse 实体建立多对一关系
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    @JsonBackReference
    private Warehouse warehouse; // 关联 Warehouse 实体

    // 动态获取 warehouseId
    public Integer getWarehouseId() {
        return warehouse != null ? warehouse.getWarehouseId() : null;
    }

    @Transient
    private String warehouseName; // 通过 Warehouse 实体获取的仓库名称
}
