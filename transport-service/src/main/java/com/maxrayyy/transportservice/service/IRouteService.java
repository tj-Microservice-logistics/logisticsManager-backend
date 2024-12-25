package com.maxrayyy.transportservice.service;

import com.maxrayyy.commonmodule.dto.transportDto.WarehouseDistanceDto;
import com.maxrayyy.transportservice.entity.Route;
import com.maxrayyy.transportservice.entity.WarehouseDistance;
import com.maxrayyy.commonmodule.dto.transportDto.RouteDto;

import java.util.List;
import java.util.Map;

public interface IRouteService {

    // 新增运输路径
    RouteDto add(RouteDto routeDto);

    // 根据仓库路径表生成图
    Map<Integer, List<WarehouseDistanceDto>> buildGraph();

    // 计算最短路径
    List<Integer> findShortestRoute(Integer startWarehouseId, Integer endWarehouseId);

    // 生成 RouteWarehouses 并保存
    void generateRouteWarehouses(List<Integer> shortestRoute, Route route);

}
