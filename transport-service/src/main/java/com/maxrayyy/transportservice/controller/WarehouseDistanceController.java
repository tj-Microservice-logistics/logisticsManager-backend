package com.maxrayyy.transportservice.controller;

import com.maxrayyy.transportservice.entity.WarehouseDistance;
import com.maxrayyy.transportservice.service.WarehouseDistanceService;
import com.maxrayyy.commonmodule.dto.transportDto.ResponseMessage;
import com.maxrayyy.commonmodule.dto.transportDto.WarehouseDistanceDto;
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

    // 增加
    @PostMapping
    public ResponseMessage<WarehouseDistanceDto> add(@RequestBody WarehouseDistanceDto warehouseDistance) {
        WarehouseDistanceDto warehouseDistanceNew = warehouseDistanceService.add(warehouseDistance);
        return ResponseMessage.success(warehouseDistanceNew);
    }
}
