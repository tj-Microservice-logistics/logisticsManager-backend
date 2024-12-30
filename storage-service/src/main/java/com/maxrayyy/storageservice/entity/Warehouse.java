// src/main/java/com/maxrayyy/storageservice/entity/Warehouse.java
package com.maxrayyy.storageservice.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "warehouses")
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "warehouse_id")
    private Integer warehouseId;

    @Column(nullable = false)
    private String warehouseName;

    @Column(nullable = false)
    private Integer capacity;

}
