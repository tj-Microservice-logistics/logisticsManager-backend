package com.maxrayyy.storageservice.entity;

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
    @Column(name = "driver_id")
    private Integer driverId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name="is_available")
    private Boolean isAvailable;

    @Column(name = "warehouse_id")
    private Integer warehouseId;

    @ManyToOne
    @JoinColumn(name = "warehouse", referencedColumnName = "warehouse_id")
    private Warehouse warehouse; // 外键，关联到 Warehouse
}
