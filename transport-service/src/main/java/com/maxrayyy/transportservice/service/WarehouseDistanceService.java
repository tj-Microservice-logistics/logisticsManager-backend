package com.maxrayyy.transportservice.service;

import com.maxrayyy.transportservice.entity.Warehouse;
import com.maxrayyy.transportservice.entity.WarehouseDistance;
import com.maxrayyy.transportservice.repository.WarehouseDistanceRepository;
import com.maxrayyy.commonmodule.dto.transportDto.WarehouseDistanceDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
