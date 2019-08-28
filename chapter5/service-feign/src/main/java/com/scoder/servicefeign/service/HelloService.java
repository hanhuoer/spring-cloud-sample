package com.scoder.servicefeign.service;

import com.scoder.servicefeign.service.impl.HelloServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author shaokang
 **/
@FeignClient(value = "SERVICE-HI", fallback = HelloServiceFallback.class)
public interface HelloService {

    @GetMapping("/hi")
    String sayHiFromClientOne(@RequestParam(value = "name") String name);

}
