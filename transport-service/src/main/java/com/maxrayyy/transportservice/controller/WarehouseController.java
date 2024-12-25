package com.maxrayyy.transportservice.controller;

import com.maxrayyy.transportservice.service.WarehouseService;
import com.maxrayyy.commonmodule.dto.transportDto.ResponseMessage;
import com.maxrayyy.commonmodule.dto.transportDto.WarehouseDto;
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
    public ResponseMessage<WarehouseDto> add(@RequestBody WarehouseDto warehouse) {
        WarehouseDto warehouseNew = warehouseService.add(warehouse);
        return ResponseMessage.success(warehouseNew);
    }

}
