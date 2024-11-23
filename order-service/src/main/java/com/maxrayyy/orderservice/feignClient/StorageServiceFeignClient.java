package com.maxrayyy.orderservice.feignClient;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "storage-service")
public interface StorageServiceFeignClient {

    // TODO:调用接口

}
