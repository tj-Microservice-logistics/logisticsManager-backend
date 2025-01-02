// src/main/java/com/maxrayyy/storageservice/controller/WarehouseController.java
package com.maxrayyy.storageservice.controller;

import com.maxrayyy.storageservice.entity.Warehouse;
import com.maxrayyy.storageservice.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/warehouses")
public class WarehouseController {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @GetMapping
    public ResponseEntity<List<Warehouse>> getAllWarehouses() {
        List<Warehouse> warehouses = warehouseRepository.findAll();
        return ResponseEntity.ok(warehouses);
    }
}
