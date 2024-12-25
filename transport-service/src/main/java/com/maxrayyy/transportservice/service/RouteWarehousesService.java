package com.maxrayyy.transportservice.service;

import com.maxrayyy.transportservice.entity.RouteWarehouses;
import com.maxrayyy.transportservice.entity.Warehouse;
import com.maxrayyy.transportservice.repository.RouteWarehousesRepository;
import com.maxrayyy.commonmodule.dto.transportDto.RouteWarehousesDto;
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
    public RouteWarehousesDto add(RouteWarehousesDto routeWarehousesDto) {

        RouteWarehouses routeWarehouses = new RouteWarehouses();
        BeanUtils.copyProperties(routeWarehousesDto, routeWarehouses);

        routeWarehousesRepository.save(routeWarehouses);

        return routeWarehousesDto;
    }

    @Override
    public List<RouteWarehousesDto> get(Integer routeId) {

        Iterable<RouteWarehouses> routeWarehouses = routeWarehousesRepository.findByRouteRouteId(routeId);

        List<RouteWarehousesDto> routeWarehousesDtoList = new ArrayList<>();

        for (RouteWarehouses rw : routeWarehouses) {
            RouteWarehousesDto dto = new RouteWarehousesDto();
            BeanUtils.copyProperties(rw, dto);
            dto.setRouteId(routeId);
            dto.setWarehouseId(rw.getWarehouse().getWarehouseId());
            routeWarehousesDtoList.add(dto);
        }

        return routeWarehousesDtoList;
    }

    @Override
    public List<RouteWarehousesDto> updateArrival(Integer warehouseId) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId).orElse(null);

        List<RouteWarehouses> routeWarehousesList = routeWarehousesRepository.findByWarehouse(warehouse);

        for (RouteWarehouses routeWarehouses : routeWarehousesList) {
            routeWarehouses.setArrival(true);
        }

        routeWarehousesList = (List<RouteWarehouses>) routeWarehousesRepository.saveAll(routeWarehousesList);

        List<RouteWarehousesDto> routeWarehousesDtoList = new ArrayList<>();
        for (RouteWarehouses rw : routeWarehousesList) {
            RouteWarehousesDto dto = new RouteWarehousesDto();
            BeanUtils.copyProperties(rw, dto);
            dto.setRouteId(rw.getRoute().getRouteId());
            dto.setWarehouseId(rw.getWarehouse().getWarehouseId());
            routeWarehousesDtoList.add(dto);
        }

        return routeWarehousesDtoList;
    }
}
