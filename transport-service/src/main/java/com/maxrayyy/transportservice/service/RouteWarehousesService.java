package com.maxrayyy.transportservice.service;

import com.maxrayyy.transportservice.entity.RouteWarehouses;
import com.maxrayyy.transportservice.entity.Warehouse;
import com.maxrayyy.transportservice.repository.RouteWarehousesRepository;
import com.maxrayyy.transportservice.dto.RouteWarehousesDto;
import com.maxrayyy.transportservice.repository.WarehouseRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RouteWarehousesService implements IRouteWarehousesService {

    @Autowired
    RouteWarehousesRepository routeWarehousesRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Override
    public RouteWarehouses add(RouteWarehousesDto routeWarehousesDto) {

        RouteWarehouses routeWarehouses = new RouteWarehouses();

        BeanUtils.copyProperties(routeWarehousesDto, routeWarehouses);

        return routeWarehousesRepository.save(routeWarehouses);
    }

    @Override
    public List<RouteWarehouses> get(Integer routeId) {

        Iterable<RouteWarehouses> routeWarehouses = routeWarehousesRepository.findByRouteRouteId(routeId);

        List<RouteWarehouses> routeWarehousesList = new ArrayList<>();
        routeWarehouses.forEach(routeWarehousesList::add);

        return routeWarehousesList;
    }

    @Override
    public List<RouteWarehouses> updateArrival(Integer warehouseId) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId).orElse(null);

        List<RouteWarehouses> routeWarehousesList = routeWarehousesRepository.findByWarehouse(warehouse);

        for (RouteWarehouses routeWarehouses : routeWarehousesList) {
            routeWarehouses.setArrival(true);
        }

        return (List<RouteWarehouses>) routeWarehousesRepository.saveAll(routeWarehousesList);
    }
}
