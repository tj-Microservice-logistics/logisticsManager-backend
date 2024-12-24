package com.maxrayyy.transportservice.Service;

import com.maxrayyy.transportservice.Pojo.WarehouseDistance;
import com.maxrayyy.transportservice.dto.WarehouseDistanceDto;

import java.util.List;
import java.util.Map;

public interface IWarehouseDistanceService {

    //增加仓库之间的路径
    WarehouseDistance add(WarehouseDistanceDto warehouseDistanceDto);
}
