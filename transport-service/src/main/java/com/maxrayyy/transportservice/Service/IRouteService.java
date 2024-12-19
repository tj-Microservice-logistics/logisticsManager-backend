package com.maxrayyy.transportservice.Service;

import com.maxrayyy.transportservice.Pojo.Route;
import com.maxrayyy.transportservice.Pojo.WarehouseDistance;
import com.maxrayyy.transportservice.dto.RouteDto;

import java.util.List;
import java.util.Map;

public interface IRouteService {

    // 新增运输路径
    Route add(RouteDto routeDto);

    // 根据仓库路径表生成图
    Map<Integer, List<WarehouseDistance>> buildGraph();

    // 计算最短路径
    List<Integer> findShortestRoute(Integer startWarehouseId, Integer endWarehouseId);

    // 生成 RouteWarehouses 并保存
    void generateRouteWarehouses(List<Integer> shortestRoute, Route route);

}
