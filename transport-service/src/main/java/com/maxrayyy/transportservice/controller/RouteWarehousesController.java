package com.maxrayyy.transportservice.controller;

import com.maxrayyy.transportservice.entity.RouteWarehouses;
import com.maxrayyy.transportservice.service.RouteWarehousesService;
import com.maxrayyy.commonmodule.dto.transportDto.ResponseMessage;
import com.maxrayyy.commonmodule.dto.transportDto.RouteWarehousesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/routeWarehouses")
public class RouteWarehousesController {

    @Autowired
    RouteWarehousesService routeWarehousesService;

    // 增加
    @PostMapping
    public ResponseMessage<RouteWarehouses> add(@RequestBody RouteWarehousesDto routeWarehouses) {
        RouteWarehouses routeWarehousesNew = routeWarehousesService.add(routeWarehouses);
        return ResponseMessage.success(routeWarehousesNew);
    }

    // 查询
    @GetMapping("/{routeId}")
    public ResponseMessage<List<RouteWarehouses>> get(@PathVariable Integer routeId) {
        List<RouteWarehouses> routeWarehousesList = routeWarehousesService.get(routeId);
        return ResponseMessage.success(routeWarehousesList);
    }

    // 更新
    @PutMapping("/arrival/{warehouseId}")
    public ResponseMessage<List<RouteWarehouses>> updateArrival(@PathVariable Integer warehouseId) {
        List<RouteWarehouses> updatedRouteWarehousesList = routeWarehousesService.updateArrival(warehouseId);
        return ResponseMessage.success(updatedRouteWarehousesList);
    }

}
