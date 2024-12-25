package com.maxrayyy.transportservice.controller;

import com.maxrayyy.commonmodule.dto.transportDto.WaybillDto;
import com.maxrayyy.transportservice.entity.Waybill;
import com.maxrayyy.transportservice.service.WaybillService;
import com.maxrayyy.commonmodule.dto.transportDto.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/waybill")
public class WaybillController {

    @Autowired
    WaybillService waybillService;

    // 查询
    @GetMapping("/allWaybills")
    public ResponseMessage<List<WaybillDto>> getAllWaybills(){
        List<WaybillDto> waybillList = waybillService.getAllWaybills();
        return ResponseMessage.success(waybillList);
    }

    // 查询
    @GetMapping("/waybillRoute")
    public ResponseMessage<List<WaybillDto>> getRouteWaybills(
            @RequestParam Integer startId,
            @RequestParam Integer endId){
        List<WaybillDto> routeWaybillList = waybillService.getRouteWaybills(startId, endId);
        return ResponseMessage.success(routeWaybillList);
    }

    // 更新
    @PutMapping("/{waybillId}")
    public ResponseMessage<WaybillDto> updateWaybill(@PathVariable Integer waybillId, @RequestBody WaybillDto waybill) {
        WaybillDto updated = waybillService.updateWaybill(waybillId, waybill);
        return ResponseMessage.success(updated);
    }
}
