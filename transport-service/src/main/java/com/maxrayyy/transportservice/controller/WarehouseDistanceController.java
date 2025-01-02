package com.maxrayyy.transportservice.controller;

import com.maxrayyy.commonmodule.dto.transportDto.WarehouseDto;
import com.maxrayyy.transportservice.service.WarehouseDistanceService;
import com.maxrayyy.commonmodule.dto.transportDto.ResponseMessage;
import com.maxrayyy.commonmodule.dto.transportDto.WarehouseDistanceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/warehouseDistance")
public class  WarehouseDistanceController {

    @Autowired
    WarehouseDistanceService warehouseDistanceService;

    // 增加
    @PostMapping
    public ResponseMessage<WarehouseDistanceDto> add(@RequestBody WarehouseDistanceDto warehouseDistance) {
        WarehouseDistanceDto warehouseDistanceNew = warehouseDistanceService.add(warehouseDistance);
        return ResponseMessage.success(warehouseDistanceNew);
    }

    // 查询
    @GetMapping("/{warehouseId}")
    public ResponseMessage<List<WarehouseDto>> getAdjacentWarehouse(@PathVariable Integer warehouseId) {
        List<WarehouseDto> warehouseDtoList = warehouseDistanceService.getAdjacentWarehouse(warehouseId);
        return ResponseMessage.success(warehouseDtoList);
    }
}
