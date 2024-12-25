package com.maxrayyy.transportservice.service;

import com.maxrayyy.commonmodule.dto.transportDto.WarehouseDistanceDto;

public interface IWarehouseDistanceService {

    //增加仓库之间的路径
    WarehouseDistanceDto add(WarehouseDistanceDto warehouseDistanceDto);
}
