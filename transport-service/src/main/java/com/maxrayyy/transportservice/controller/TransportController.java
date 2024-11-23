package com.maxrayyy.transportservice.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transport")
@RefreshScope
public class TransportController {

    @GetMapping(value = "/test")
    public String test() {
        return "this is transport-srevice";
    }
}
