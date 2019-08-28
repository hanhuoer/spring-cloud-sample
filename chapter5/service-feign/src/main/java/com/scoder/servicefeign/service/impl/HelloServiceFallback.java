package com.scoder.servicefeign.service.impl;

import com.scoder.servicefeign.service.HelloService;
import org.springframework.stereotype.Component;

/**
 * @author shaokang
 **/
@Component
public class HelloServiceFallback implements HelloService {
    @Override
    public String sayHiFromClientOne(String name) {
        return "hi, " + name + ", sorry,error!";
    }
}
