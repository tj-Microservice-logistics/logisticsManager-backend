package com.maxrayyy.transportservice.Service;

import com.maxrayyy.transportservice.Pojo.RouteWarehouses;
import com.maxrayyy.transportservice.dto.RouteWarehousesDto;

import java.util.List;

public interface IRouteWarehousesService {

    // 新增路径仓库
    RouteWarehouses add(RouteWarehousesDto routeWarehousesDto);

    // 查询一个路径上的全部仓库
    List<RouteWarehouses> get(Integer routeId);
}
