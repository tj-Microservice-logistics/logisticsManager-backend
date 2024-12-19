package com.maxrayyy.transportservice.Service;

import com.maxrayyy.transportservice.Pojo.Warehouse;
import com.maxrayyy.transportservice.dto.WarehouseDto;

public interface IWarehouseService {
    Warehouse add(WarehouseDto warehouseDto);
}
