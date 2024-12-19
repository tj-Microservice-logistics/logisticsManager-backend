package com.maxrayyy.transportservice.controller;

import com.maxrayyy.transportservice.Pojo.Waybill;
import com.maxrayyy.transportservice.Service.WaybillService;
import com.maxrayyy.transportservice.dto.ResponseMessage;
import org.apache.http.HttpStatus;
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
    public ResponseMessage<List<Waybill>> getAllWaybills(){
        List<Waybill> waybillList = waybillService.getAllWaybills();
        return ResponseMessage.success(waybillList);
    }

    // 查询
    @GetMapping("/waybillRoute")
    public ResponseMessage<List<Waybill>> getRouteWaybills(
            @RequestParam Integer startId,
            @RequestParam Integer endId){
        List<Waybill> routeWaybillList = waybillService.getRouteWaybills(startId, endId);
        return ResponseMessage.success(routeWaybillList);
    }

    // 更新
    @PutMapping("/{waybillId}")
    public ResponseMessage<Waybill> updateWaybill(@PathVariable Integer waybillId, @RequestBody Waybill waybill) {
        Waybill updated = waybillService.updateWaybill(waybillId, waybill);
        return ResponseMessage.success(updated);
    }
}
