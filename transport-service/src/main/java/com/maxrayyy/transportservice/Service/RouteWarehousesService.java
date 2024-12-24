package com.maxrayyy.transportservice.Service;

import com.maxrayyy.transportservice.Pojo.RouteWarehouses;
import com.maxrayyy.transportservice.Repository.RouteWarehousesRepository;
import com.maxrayyy.transportservice.dto.RouteWarehousesDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RouteWarehousesService implements IRouteWarehousesService {

    @Autowired
    RouteWarehousesRepository routeWarehousesRepository;

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
}
