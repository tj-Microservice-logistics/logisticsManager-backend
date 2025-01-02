package com.maxrayyy.transportservice.controller;

import com.maxrayyy.commonmodule.dto.transportDto.WaybillDto;
import com.maxrayyy.transportservice.service.WaybillService;
import com.maxrayyy.commonmodule.dto.transportDto.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/waybill")
public class WaybillController {

    @Autowired
    WaybillService waybillService;

    // 查询所有运单
    @GetMapping("/allWaybills")
    public ResponseMessage<List<WaybillDto>> getAllWaybills(){
        List<WaybillDto> waybillList = waybillService.getAllWaybills();
        return ResponseMessage.success(waybillList);
    }

    // 根据起点和终点查询“待发车”运单
    @GetMapping("/waybillRoute")
    public ResponseMessage<List<WaybillDto>> getRouteWaybills(
            @RequestParam Integer startId,
            @RequestParam Integer endId){
        List<WaybillDto> routeWaybillList = waybillService.getRouteWaybills(startId, endId);
        return ResponseMessage.success(routeWaybillList);
    }

    // 根据车辆查询“运输中”运单
    @GetMapping("/waybillVehicle")
    public ResponseMessage<List<WaybillDto>> getVehicleWaybills(
            @RequestParam String vehiclePlateNumber){
        List<WaybillDto> vehicleWaybillList = waybillService.getVehicleWaybills(vehiclePlateNumber);
        return ResponseMessage.success(vehicleWaybillList);
    }

    // 更新运单内容
    @PutMapping("/{waybillId}")
    public ResponseMessage<WaybillDto> updateWaybill(@PathVariable Integer waybillId, @RequestBody WaybillDto waybill) {
        WaybillDto updated = waybillService.updateWaybill(waybillId, waybill);
        return ResponseMessage.success(updated);
    }
}
