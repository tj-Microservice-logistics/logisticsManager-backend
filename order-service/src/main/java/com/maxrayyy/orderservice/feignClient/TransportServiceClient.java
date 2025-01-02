package com.maxrayyy.orderservice.feignClient;

import com.maxrayyy.commonmodule.dto.transportDto.ResponseMessage;
import com.maxrayyy.commonmodule.dto.transportDto.RouteDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "transport-service")
public interface TransportServiceClient {

    // 新增订单对应的路径
    @PostMapping("/route")
    ResponseMessage<RouteDto> addRoute(@RequestBody RouteDto routeDto);
}
