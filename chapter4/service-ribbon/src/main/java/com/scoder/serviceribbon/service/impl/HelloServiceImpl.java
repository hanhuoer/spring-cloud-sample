package com.scoder.serviceribbon.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.scoder.serviceribbon.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author shaokang
 **/
@Service
public class HelloServiceImpl implements HelloService {

    @Autowired
    RestTemplate restTemplate;

    /**
     * 开启熔断器功能, 并指定 fallbackMethod
     *
     * @param name name
     * @return String
     */
    @Override
    @HystrixCommand(fallbackMethod = "hiBack")
    public String hiService(String name) {
        return restTemplate.getForObject("http://SERVICE-HI/hi?name=" + name, String.class);
    }

    private String hiBack(String name) {
        return "hi, " + name + ", sorry,error!";
    }
}
