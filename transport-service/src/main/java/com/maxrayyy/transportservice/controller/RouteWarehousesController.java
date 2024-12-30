package com.maxrayyy.transportservice.controller;

import com.maxrayyy.transportservice.service.RouteWarehousesService;
import com.maxrayyy.commonmodule.dto.transportDto.ResponseMessage;
import com.maxrayyy.commonmodule.dto.transportDto.RouteWarehousesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/routeWarehouses")
public class RouteWarehousesController {

    @Autowired
    RouteWarehousesService routeWarehousesService;

    // 增加
    @PostMapping
    public ResponseMessage<RouteWarehousesDto> add(@RequestBody RouteWarehousesDto routeWarehouses) {
        RouteWarehousesDto routeWarehousesNew = routeWarehousesService.add(routeWarehouses);
        return ResponseMessage.success(routeWarehousesNew);
    }

    // 查询
    @GetMapping("/{orderNumber}")
    public ResponseMessage<List<RouteWarehousesDto>> get(@PathVariable String orderNumber) {
        List<RouteWarehousesDto> routeWarehousesList = routeWarehousesService.get(orderNumber);
        return ResponseMessage.success(routeWarehousesList);
    }

    // 更新
    @PutMapping("/arrival")
    public ResponseMessage<List<RouteWarehousesDto>> updateArrival(
            @RequestParam String vehiclePlateNumber) {
        List<RouteWarehousesDto> updatedRouteWarehousesList = routeWarehousesService.updateArrival(vehiclePlateNumber);
        return ResponseMessage.success(updatedRouteWarehousesList);
    }

}
