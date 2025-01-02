package com.maxrayyy.transportservice.service;

import com.maxrayyy.commonmodule.dto.transportDto.WarehouseDistanceDto;
import com.maxrayyy.commonmodule.dto.transportDto.WarehouseDto;

import java.util.List;

public interface IWarehouseDistanceService {

    // 增加仓库之间的路径
    WarehouseDistanceDto add(WarehouseDistanceDto warehouseDistanceDto);

    // 获取仓库的相邻可达仓库
    List<WarehouseDto> getAdjacentWarehouse(Integer warehouseId);
}
