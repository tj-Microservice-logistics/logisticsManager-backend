package com.maxrayyy.storageservice.feignClient;

import com.maxrayyy.commonmodule.dto.transportDto.ResponseMessage;
import com.maxrayyy.commonmodule.dto.transportDto.RouteWarehousesDto;
import com.maxrayyy.commonmodule.dto.transportDto.WaybillDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "transport-service", url = "http://localhost:9082")
public interface TransportServiceClient {

    // 根据起点和终点查询“待发车”运单
    @GetMapping("/waybill/waybillRoute")
    ResponseMessage<List<WaybillDto>> getRouteWaybills(
            @RequestParam("startId") Integer startId,
            @RequestParam("endId") Integer endId);

    // 根据车辆查询“运输中”运单
    @GetMapping("/waybill/waybillVehicle")
    public ResponseMessage<List<WaybillDto>> getVehicleWaybills(
            @RequestParam("vehiclePlateNumber") String vehiclePlateNumber);

    // 更新运单内容
    @PutMapping("/waybill/{waybillId}")
    ResponseMessage<WaybillDto> updateWaybill(
            @PathVariable("waybillId") Integer waybillId,
            @RequestBody WaybillDto waybill);


    @PutMapping("/routeWarehouses/arrival")
    ResponseMessage<List<RouteWarehousesDto>> updateArrival(
            @RequestParam("vehiclePlateNumber") String vehiclePlateNumber);

}
