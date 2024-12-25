package com.maxrayyy.storageservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "drivers")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer driverId;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String contactNumber;

    @Column(nullable = false,name="is_available")
    private Boolean isAvailable;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_vehicle_id")
    private Vehicle assignedVehicle; // 关联车辆

    // 多对一关系，避免序列化时无限递归
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    @JsonBackReference
    private Warehouse warehouse;

    @Column(name = "warehouse_id", insertable = false, updatable = false)
    private Integer warehouseId; // 外键，关联到 Warehouse

    @Transient
    private String warehouseName; // 通过 Warehouse 实体获取的仓库名称
}
