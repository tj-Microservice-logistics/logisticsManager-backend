package com.maxrayyy.transportservice.controller;

import com.maxrayyy.transportservice.entity.Route;
import com.maxrayyy.transportservice.service.IRouteService;
import com.maxrayyy.commonmodule.dto.transportDto.ResponseMessage;
import com.maxrayyy.commonmodule.dto.transportDto.RouteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/route")
public class RouteController {

    @Autowired
    IRouteService routeService;

    //增加
    @PostMapping
    public ResponseMessage<Route> add(@RequestBody RouteDto route) {
        Route routeNew = routeService.add(route);
        return ResponseMessage.success(routeNew);
    }

}
