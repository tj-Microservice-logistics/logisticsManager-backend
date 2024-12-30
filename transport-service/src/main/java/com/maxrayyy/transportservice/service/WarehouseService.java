package com.maxrayyy.transportservice.service;

import com.maxrayyy.transportservice.entity.Warehouse;
import com.maxrayyy.transportservice.repository.WarehouseRepository;
import com.maxrayyy.commonmodule.dto.transportDto.WarehouseDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WarehouseService implements IWarehouseService {

    @Autowired
    WarehouseRepository warehouseRepository;

    @Override
    public WarehouseDto add(WarehouseDto warehouseDto) {
        Warehouse warehouse = new Warehouse();
        BeanUtils.copyProperties(warehouseDto, warehouse);

        warehouseRepository.save(warehouse);

        return warehouseDto;
    }

    @Override
    public WarehouseDto get(Integer warehouseId) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId).orElse(null);
        if (warehouse == null) {
            return null;
        }
        WarehouseDto warehouseDto = new WarehouseDto();
        BeanUtils.copyProperties(warehouse, warehouseDto);
        return warehouseDto;
    }
}
