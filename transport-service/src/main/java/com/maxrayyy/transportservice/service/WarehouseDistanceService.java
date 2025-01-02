package com.maxrayyy.transportservice.service;

import com.maxrayyy.commonmodule.dto.transportDto.WarehouseDto;
import com.maxrayyy.transportservice.entity.Warehouse;
import com.maxrayyy.transportservice.entity.WarehouseDistance;
import com.maxrayyy.transportservice.repository.WarehouseDistanceRepository;
import com.maxrayyy.commonmodule.dto.transportDto.WarehouseDistanceDto;
import com.maxrayyy.transportservice.repository.WarehouseRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class WarehouseDistanceService implements IWarehouseDistanceService {

    @Autowired
    WarehouseDistanceRepository warehouseDistanceRepository;
    @Autowired
    private WarehouseRepository warehouseRepository;

    @Override
    public WarehouseDistanceDto add(WarehouseDistanceDto warehouseDistanceDto) {
        WarehouseDistance warehouseDistance = new WarehouseDistance();

        Warehouse warehouse1 = warehouseDistance.getWarehouse1();
        Warehouse warehouse2 = warehouseDistance.getWarehouse2();

        //判断路径是否重复
        if (warehouseDistanceRepository.existsByWarehouse1AndWarehouse2(warehouse1, warehouse2)) {
            throw new RuntimeException("此仓库路径已存在！");
        }

        BeanUtils.copyProperties(warehouseDistanceDto,warehouseDistance);

        warehouseDistanceRepository.save(warehouseDistance);

        return warehouseDistanceDto;
    }

    @Override
    public List<WarehouseDto> getAdjacentWarehouse(Integer warehouseId) {
        Iterable<WarehouseDistance> warehouseDistances = warehouseDistanceRepository.findAll();

        Set<Integer> adjacentWarehouseIds = new HashSet<>();

        for (WarehouseDistance warehouseDistance : warehouseDistances) {
            if (warehouseDistance.getWarehouse1().getWarehouseId().equals(warehouseId)) {
                adjacentWarehouseIds.add(warehouseDistance.getWarehouse2().getWarehouseId());
            }
        }

        List<WarehouseDto> warehouseDtos = new ArrayList<>();
        for (Integer adjacentWarehouseId : adjacentWarehouseIds) {
            Warehouse warehouse = warehouseRepository.findById(adjacentWarehouseId)
                    .orElseThrow(() -> new RuntimeException("相邻可达仓库不存在！"));
            WarehouseDto warehouseDto = new WarehouseDto();
            BeanUtils.copyProperties(warehouse,warehouseDto);
            warehouseDtos.add(warehouseDto);
        }
        return warehouseDtos;
    }

}
