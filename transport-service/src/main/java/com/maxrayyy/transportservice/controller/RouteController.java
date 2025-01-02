package com.maxrayyy.transportservice.controller;

import com.maxrayyy.transportservice.service.IRouteService;
import com.maxrayyy.commonmodule.dto.transportDto.ResponseMessage;
import com.maxrayyy.commonmodule.dto.transportDto.RouteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/route")
public class RouteController {

    @Autowired
    IRouteService routeService;

    // 增加路径
    @PostMapping
    public ResponseMessage<RouteDto> add(@RequestBody RouteDto route) {
        RouteDto routeNew = routeService.add(route);
        return ResponseMessage.success(routeNew);
    }

}
