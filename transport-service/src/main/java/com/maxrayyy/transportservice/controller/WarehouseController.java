package com.maxrayyy.transportservice.controller;

import com.maxrayyy.transportservice.Pojo.Warehouse;
import com.maxrayyy.transportservice.Service.WarehouseService;
import com.maxrayyy.transportservice.dto.ResponseMessage;
import com.maxrayyy.transportservice.dto.WarehouseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {

    @Autowired
    WarehouseService warehouseService;

    //增加
    @PostMapping
    public ResponseMessage<Warehouse> add(@RequestBody WarehouseDto warehouse) {
        Warehouse warehouseNew = warehouseService.add(warehouse);
        return ResponseMessage.success(warehouseNew);
    }

}
