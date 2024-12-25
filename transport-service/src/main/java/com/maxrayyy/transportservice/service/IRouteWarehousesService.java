package com.maxrayyy.transportservice.service;

import com.maxrayyy.transportservice.entity.RouteWarehouses;
import com.maxrayyy.commonmodule.dto.transportDto.RouteWarehousesDto;

import java.util.List;

public interface IRouteWarehousesService {

    // 新增路径仓库
    RouteWarehouses add(RouteWarehousesDto routeWarehousesDto);

    // 查询一个路径上的全部仓库
    List<RouteWarehouses> get(Integer routeId);

    // 更新仓库的到达状态
    List<RouteWarehouses> updateArrival(Integer warehouseId);
}
