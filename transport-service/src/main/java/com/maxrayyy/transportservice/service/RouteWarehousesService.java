package com.maxrayyy.transportservice.service;

import com.maxrayyy.transportservice.entity.Route;
import com.maxrayyy.transportservice.entity.RouteWarehouses;
import com.maxrayyy.transportservice.entity.Waybill;
import com.maxrayyy.transportservice.feignClient.OrderServiceClient;
import com.maxrayyy.transportservice.repository.RouteRepository;
import com.maxrayyy.transportservice.repository.RouteWarehousesRepository;
import com.maxrayyy.commonmodule.dto.transportDto.RouteWarehousesDto;
import com.maxrayyy.transportservice.repository.WaybillRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class RouteWarehousesService implements IRouteWarehousesService {

    @Autowired
    RouteWarehousesRepository routeWarehousesRepository;

    @Autowired
    RouteRepository routeRepository;

    @Autowired
    WaybillRepository waybillRepository;

    @Autowired
    OrderServiceClient orderServiceClient;

    @Override
    public RouteWarehousesDto add(RouteWarehousesDto routeWarehousesDto) {

        RouteWarehouses routeWarehouses = new RouteWarehouses();
        BeanUtils.copyProperties(routeWarehousesDto, routeWarehouses);

        routeWarehousesRepository.save(routeWarehouses);

        return routeWarehousesDto;
    }

    @Override
    public List<RouteWarehousesDto> get(String orderNumber) {
        Route route = routeRepository.findByOrderNumber(orderNumber);

        Iterable<RouteWarehouses> routeWarehouses = routeWarehousesRepository.findByRouteRouteId(route.getRouteId());

        List<RouteWarehousesDto> routeWarehousesDtoList = new ArrayList<>();

        for (RouteWarehouses rw : routeWarehouses) {
            RouteWarehousesDto dto = new RouteWarehousesDto();
            BeanUtils.copyProperties(rw, dto);
            dto.setRouteId(route.getRouteId());
            dto.setWarehouseId(rw.getWarehouse().getWarehouseId());
            routeWarehousesDtoList.add(dto);
        }

        return routeWarehousesDtoList;
    }

    @Override
    public List<RouteWarehousesDto> updateArrival(String vehiclePlateNumber) {
        Iterable<Waybill> waybillList = waybillRepository.findByVehiclePlateNumber(vehiclePlateNumber);

        List<RouteWarehousesDto> routeWarehousesDtoList = new ArrayList<>();
        for (Waybill waybill : waybillList) {
            if (Objects.equals(waybill.getTransportStatus(), "运输中")) {
                RouteWarehouses routeWarehouses = routeWarehousesRepository.findByWarehouseAndRoute(waybill.getEnd(), waybill.getRoute());
                routeWarehouses.setArrival(true);
                routeWarehousesRepository.save(routeWarehouses);

                // 如果是终点仓库，结束订单
                if (routeWarehouses.getWarehouse() == routeWarehouses.getRoute().getEndWarehouse()) {
                    orderServiceClient.updateDeliverStatus(routeWarehouses.getRoute().getOrderNumber());
                }

                RouteWarehousesDto routeWarehousesDto = new RouteWarehousesDto();
                BeanUtils.copyProperties(routeWarehouses, routeWarehousesDto);
                routeWarehousesDto.setRouteId(routeWarehouses.getRoute().getRouteId());
                routeWarehousesDto.setWarehouseId(routeWarehouses.getWarehouse().getWarehouseId());
                routeWarehousesDtoList.add(routeWarehousesDto);
            }
        }

        return routeWarehousesDtoList;
    }
}
