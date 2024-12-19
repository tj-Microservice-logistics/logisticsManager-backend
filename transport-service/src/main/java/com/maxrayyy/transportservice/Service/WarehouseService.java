package com.maxrayyy.transportservice.Service;

import com.maxrayyy.transportservice.Pojo.Warehouse;
import com.maxrayyy.transportservice.Repository.WarehouseRepository;
import com.maxrayyy.transportservice.dto.WarehouseDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WarehouseService implements IWarehouseService {

    @Autowired
    WarehouseRepository warehouseRepository;

    @Override
    public Warehouse add(WarehouseDto warehouseDto) {
        Warehouse warehouse = new Warehouse();

        BeanUtils.copyProperties(warehouseDto, warehouse);

        return warehouseRepository.save(warehouse);
    }
}
