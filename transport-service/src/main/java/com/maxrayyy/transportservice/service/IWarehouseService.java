package com.maxrayyy.transportservice.service;

import com.maxrayyy.transportservice.entity.Warehouse;
import com.maxrayyy.transportservice.dto.WarehouseDto;

public interface IWarehouseService {
    Warehouse add(WarehouseDto warehouseDto);
}
