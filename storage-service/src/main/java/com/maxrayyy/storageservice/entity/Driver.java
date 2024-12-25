package com.maxrayyy.storageservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "drivers")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assigned_vehicle_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Vehicle assignedVehicle;



    // 多对一关系，避免序列化时无限递归
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse_id")
    @JsonBackReference
    private Warehouse warehouse;

    @Column(name = "warehouse_id", insertable = false, updatable = false)
    private Integer warehouseId; // 外键，关联到 Warehouse

    @Transient
    private String warehouseName; // 通过 Warehouse 实体获取的仓库名称
}
