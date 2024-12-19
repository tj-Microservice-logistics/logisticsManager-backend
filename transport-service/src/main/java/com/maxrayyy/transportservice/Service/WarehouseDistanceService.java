package com.maxrayyy.transportservice.Service;

import com.maxrayyy.transportservice.Pojo.Warehouse;
import com.maxrayyy.transportservice.Pojo.WarehouseDistance;
import com.maxrayyy.transportservice.Repository.WarehouseDistanceRepository;
import com.maxrayyy.transportservice.dto.WarehouseDistanceDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WarehouseDistanceService implements IWarehouseDistanceService {

    @Autowired
    WarehouseDistanceRepository warehouseDistanceRepository;

    @Override
    public WarehouseDistance add(WarehouseDistanceDto warehouseDistanceDto) {
        WarehouseDistance warehouseDistance = new WarehouseDistance();

        Warehouse warehouse1 = warehouseDistance.getWarehouse1();
        Warehouse warehouse2 = warehouseDistance.getWarehouse2();

        //判断路径是否重复
        if (warehouseDistanceRepository.existsByWarehouse1AndWarehouse2(warehouse1, warehouse2)) {
            throw new RuntimeException("此仓库路径已存在！");
        }

        BeanUtils.copyProperties(warehouseDistanceDto,warehouseDistance);

        return warehouseDistanceRepository.save(warehouseDistance);
    }

}
