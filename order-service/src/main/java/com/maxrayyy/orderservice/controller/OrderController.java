package com.maxrayyy.orderservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@RefreshScope
public class OrderController {

    @Value("${config.info}")
    private String configInfo;

    @GetMapping(value = "/test")
    public String test(){
        return "this is order-service";
    }

    @GetMapping(value = "/test/getConfigInfo")
    public String getConfigInfo(){
        return configInfo;
    }

}
