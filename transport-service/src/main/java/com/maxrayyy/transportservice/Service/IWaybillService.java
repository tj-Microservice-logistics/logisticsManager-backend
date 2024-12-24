package com.maxrayyy.transportservice.Service;

import com.maxrayyy.transportservice.Pojo.Route;
import com.maxrayyy.transportservice.Pojo.Waybill;

import java.util.List;

public interface IWaybillService {

    // 根据规划路径新增对应订单
    void generateWaybills(List<Integer> routeWarehouseIds, Route route);

    // 查询全部运单
    List<Waybill> getAllWaybills();

    // 根据起点仓库和终点仓库查询运单
    List<Waybill> getRouteWaybills(Integer startId, Integer endId);

    // 更新运单信息，分配司机车辆
    Waybill updateWaybill(Integer waybillId, Waybill waybill);
}
