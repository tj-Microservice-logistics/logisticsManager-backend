package com.maxrayyy.transportservice.controller;

import com.maxrayyy.transportservice.service.WarehouseService;
import com.maxrayyy.commonmodule.dto.transportDto.ResponseMessage;
import com.maxrayyy.commonmodule.dto.transportDto.WarehouseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/warehouse")
public class WarehouseController {

    @Autowired
    WarehouseService warehouseService;

    // 增加仓库
    @PostMapping
    public ResponseMessage<WarehouseDto> add(@RequestBody WarehouseDto warehouse) {
        WarehouseDto warehouseNew = warehouseService.add(warehouse);
        return ResponseMessage.success(warehouseNew);
    }

    // 根据仓库Id查询仓库
    @GetMapping("/{warehouseId}")
    public ResponseMessage<WarehouseDto> get(@PathVariable Integer warehouseId) {
        WarehouseDto warehouseDto = warehouseService.get(warehouseId);
        return ResponseMessage.success(warehouseDto);
    }

}
