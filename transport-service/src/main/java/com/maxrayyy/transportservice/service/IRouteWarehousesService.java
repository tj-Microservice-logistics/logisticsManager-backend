package com.maxrayyy.transportservice.service;

import com.maxrayyy.commonmodule.dto.transportDto.RouteWarehousesDto;

import java.util.List;

public interface IRouteWarehousesService {

    // 新增路径仓库
    RouteWarehousesDto add(RouteWarehousesDto routeWarehousesDto);

    // 查询一个订单的全部仓库
    List<RouteWarehousesDto> get(String orderNumber);

    // 更新仓库的到达状态
    List<RouteWarehousesDto> updateArrival(String vehiclePlateNumber);
}
