package com.maxrayyy.transportservice.controller;

import com.maxrayyy.transportservice.Pojo.WarehouseDistance;
import com.maxrayyy.transportservice.Service.WarehouseDistanceService;
import com.maxrayyy.transportservice.dto.ResponseMessage;
import com.maxrayyy.transportservice.dto.WarehouseDistanceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/warehouseDistance")
public class WarehouseDistanceController {

    @Autowired
    WarehouseDistanceService warehouseDistanceService;

    //增加
    @PostMapping
    public ResponseMessage<WarehouseDistance> add(@RequestBody WarehouseDistanceDto warehouseDistance) {
        WarehouseDistance warehouseDistanceNew = warehouseDistanceService.add(warehouseDistance);
        return ResponseMessage.success(warehouseDistanceNew);
    }
}
