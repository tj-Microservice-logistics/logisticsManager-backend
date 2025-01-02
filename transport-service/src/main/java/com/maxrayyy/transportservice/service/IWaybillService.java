package com.maxrayyy.transportservice.service;

import com.maxrayyy.commonmodule.dto.transportDto.WaybillDto;
import com.maxrayyy.transportservice.entity.Route;

import java.util.List;

public interface IWaybillService {

    // 根据规划路径新增对应运单
    void generateWaybills(List<Integer> routeWarehouseIds, Route route);

    // 查询全部运单
    List<WaybillDto> getAllWaybills();

    // 根据起点仓库和终点仓库查询运单
    List<WaybillDto> getRouteWaybills(Integer startId, Integer endId);

    // 根据车辆查询运单
    List<WaybillDto> getVehicleWaybills(String vehiclePlateNumber);

    // 更新运单到达信息，分配司机车辆
    WaybillDto updateWaybill(Integer waybillId, WaybillDto waybillDto);

}
