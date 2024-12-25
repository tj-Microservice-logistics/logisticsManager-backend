package com.maxrayyy.transportservice.service;

import com.maxrayyy.transportservice.entity.WarehouseDistance;
import com.maxrayyy.commonmodule.dto.transportDto.WarehouseDistanceDto;

public interface IWarehouseDistanceService {

    //增加仓库之间的路径
    WarehouseDistance add(WarehouseDistanceDto warehouseDistanceDto);
}
