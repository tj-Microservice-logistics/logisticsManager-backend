package com.maxrayyy.reportservice.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
@RefreshScope
public class ReportController {

    @GetMapping(value = "/test")
    public String test(){
        return "this is report-service";
    }
}
