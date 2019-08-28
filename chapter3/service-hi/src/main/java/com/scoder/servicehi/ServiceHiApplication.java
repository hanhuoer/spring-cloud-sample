package com.scoder.servicehi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 创建服务提供者
 * eureka 客户端服务
 * {@code org.springframework.cloud.netflix.eureka.EnableEurekaClient} 表明自己是一个 client
 *
 * @author shaokang
 */
@SpringBootApplication
@EnableEurekaClient
public class ServiceHiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceHiApplication.class, args);
    }

}
