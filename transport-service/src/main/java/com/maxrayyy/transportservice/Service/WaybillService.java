package com.maxrayyy.transportservice.Service;

import com.maxrayyy.transportservice.Pojo.Route;
import com.maxrayyy.transportservice.Pojo.Warehouse;
import com.maxrayyy.transportservice.Pojo.WarehouseDistance;
import com.maxrayyy.transportservice.Pojo.Waybill;
import com.maxrayyy.transportservice.Repository.RouteRepository;
import com.maxrayyy.transportservice.Repository.WarehouseDistanceRepository;
import com.maxrayyy.transportservice.Repository.WarehouseRepository;
import com.maxrayyy.transportservice.Repository.WaybillRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

            waybillRepository.save(waybill);
        }

        route.setTotalCost(totalCost);

        routeRepository.save(route);
    }

    @Override
    public List<Waybill> getAllWaybills() {
        Iterable<Waybill> waybills = waybillRepository.findAll();

        List<Waybill> waybillList = new ArrayList<>();
        waybills.forEach(waybillList::add);

        return waybillList;
    }

    @Override
    public List<Waybill> getRouteWaybills(Integer startId, Integer endId) {

        Warehouse startWarehouse = warehouseRepository.findById(startId).orElse(null);
        Warehouse endWarehouse = warehouseRepository.findById(endId).orElse(null);

        Iterable<Waybill> routeWaybills = waybillRepository.findByStartAndEnd(startWarehouse, endWarehouse);

        List<Waybill> routeWaybillList = new ArrayList<>();
        routeWaybills.forEach(routeWaybillList::add);

        return routeWaybillList;
    }

    @Override
    @Transactional
    public Waybill updateWaybill(Integer waybillId, Waybill waybill) {
        Waybill updatedWaybill = waybillRepository.findById(waybillId).orElseThrow(() -> new IllegalArgumentException("待更新订单不存在！"));

        BeanUtils.copyProperties(waybill, updatedWaybill, "waybillId");

        return waybillRepository.save(updatedWaybill);
    }
}
