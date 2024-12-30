package com.maxrayyy.transportservice.service;

import com.maxrayyy.commonmodule.dto.transportDto.WarehouseDto;

public interface
IWarehouseService {

    // 新增一个仓库
    WarehouseDto add(WarehouseDto warehouseDto);

    // 根据仓库Id查询仓库
    WarehouseDto get(Integer warehouseId);
}
