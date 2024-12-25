package com.maxrayyy.transportservice.service;

import com.maxrayyy.commonmodule.dto.transportDto.WaybillDto;
import com.maxrayyy.transportservice.entity.Route;
import com.maxrayyy.transportservice.entity.Warehouse;
import com.maxrayyy.transportservice.entity.WarehouseDistance;
import com.maxrayyy.transportservice.entity.Waybill;
import com.maxrayyy.transportservice.repository.RouteRepository;
import com.maxrayyy.transportservice.repository.WarehouseDistanceRepository;
import com.maxrayyy.transportservice.repository.WarehouseRepository;
import com.maxrayyy.transportservice.repository.WaybillRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class WaybillService implements IWaybillService {

    @Autowired
    WaybillRepository waybillRepository;

    @Autowired
    WarehouseRepository warehouseRepository;

    @Autowired
    WarehouseDistanceRepository warehouseDistanceRepository;

    @Autowired
    RouteRepository routeRepository;

    @Override
    public void generateWaybills(List<Integer> routeWarehouseIds, Route route) {

        int totalCost = 0;

        for (int i = 0; i < routeWarehouseIds.size() - 1; i++) {
            Waybill waybill = new Waybill();

            // 获取起点和终点仓库
            int finalI = i;
            Warehouse start = warehouseRepository.findById(routeWarehouseIds.get(i))
                    .orElseThrow(() -> new IllegalArgumentException("未找到 Id : " + routeWarehouseIds.get(finalI) + " 的仓库！"));
            Warehouse end = warehouseRepository.findById(routeWarehouseIds.get(i + 1))
                    .orElseThrow(() -> new IllegalArgumentException("未找到 Id : " + routeWarehouseIds.get(finalI + 1) + " 的仓库！"));

            // 设置起点和终点
            waybill.setStart(start);
            waybill.setEnd(end);

            WarehouseDistance distance = warehouseDistanceRepository
                    .findByWarehouse1AndWarehouse2(start, end)
                    .orElseThrow(() -> new IllegalArgumentException("未找到路径：" + start.getWarehouseName() + " -> " + end.getWarehouseName()));

            // 添加成本
            totalCost += distance.getCost();

            waybill.setRoute(route);
            waybill.setCargoWeight(route.getCargoWeight());

            // 获取当前时间
            LocalDateTime now = LocalDateTime.now();

            // 格式转换为Timestamp
            Timestamp timestamp = Timestamp.valueOf(now);

            waybill.setCreatedAt(timestamp);

            waybillRepository.save(waybill);
        }

        route.setTotalCost(totalCost);

        routeRepository.save(route);

    }

    @Override
    public List<WaybillDto> getAllWaybills() {
        Iterable<Waybill> waybills = waybillRepository.findAll();

        List<WaybillDto> waybillDtoList = new ArrayList<>();
        for (Waybill waybill : waybills) {
            WaybillDto waybillDto = new WaybillDto();
            BeanUtils.copyProperties(waybill, waybillDto);
            waybillDto.setRouteId(waybill.getRoute().getRouteId());
            waybillDto.setStartWarehouseId(waybill.getStart().getWarehouseId());
            waybillDto.setEndWarehouseId(waybill.getEnd().getWarehouseId());
            waybillDtoList.add(waybillDto);
        }

        return waybillDtoList;
    }

    @Override
    public List<WaybillDto> getRouteWaybills(Integer startId, Integer endId) {

        Warehouse startWarehouse = warehouseRepository.findById(startId).orElse(null);
        Warehouse endWarehouse = warehouseRepository.findById(endId).orElse(null);

        Iterable<Waybill> routeWaybills = waybillRepository.findByStartAndEnd(startWarehouse, endWarehouse);

        List<WaybillDto> routeWaybillDtoList = new ArrayList<>();
        for (Waybill waybill : routeWaybills) {
            WaybillDto waybillDto = new WaybillDto();
            BeanUtils.copyProperties(waybill, waybillDto);
            waybillDto.setRouteId(waybill.getRoute().getRouteId());
            waybillDto.setStartWarehouseId(waybill.getStart().getWarehouseId());
            waybillDto.setEndWarehouseId(waybill.getEnd().getWarehouseId());
            routeWaybillDtoList.add(waybillDto);
        }

        return routeWaybillDtoList;
    }

    @Override
    @Transactional
    public WaybillDto updateWaybill(Integer waybillId, WaybillDto waybillDto) {

        Waybill updatedWaybill = waybillRepository.findById(waybillId).orElseThrow(() -> new IllegalArgumentException("待更新订单不存在！"));

        BeanUtils.copyProperties(waybillDto, updatedWaybill, "waybillId", "createdAt", "orderId", "cargoWeight");

        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();

        // 格式转换为Timestamp
        Timestamp timestamp = Timestamp.valueOf(now);

        updatedWaybill.setUpdatedAt(timestamp);

        // 保存更新后的实体
        updatedWaybill = waybillRepository.save(updatedWaybill);

        WaybillDto updatedWaybillDto = new WaybillDto();
        BeanUtils.copyProperties(updatedWaybill, updatedWaybillDto);
        updatedWaybillDto.setRouteId(updatedWaybill.getRoute().getRouteId());
        updatedWaybillDto.setStartWarehouseId(updatedWaybill.getStart().getWarehouseId());
        updatedWaybillDto.setEndWarehouseId(updatedWaybill.getEnd().getWarehouseId());

        return updatedWaybillDto;
    }
}
