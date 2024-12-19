package com.maxrayyy.transportservice.controller;

import com.maxrayyy.transportservice.Pojo.RouteWarehouses;
import com.maxrayyy.transportservice.Service.RouteWarehousesService;
import com.maxrayyy.transportservice.dto.ResponseMessage;
import com.maxrayyy.transportservice.dto.RouteWarehousesDto;
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

}
