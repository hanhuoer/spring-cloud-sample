chapter7 - 分布式高可用配置中心 Spring Cloud Config
---

## 目录

```
project
│
└───eureka-server
│   
└───config-server
│   
└───config-client
```

## config-server

引入依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

启动类加入 `@EnableEurekaClient` 注解

配置文件

```yml
eureka:
  client:
    service-url:
      defaultZone: http://localhost:20000/eureka/
```

## config-client

引入依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

```yml
spring: 
  application:
    name: config-client
  cloud:
    config:
      discovery:
        enabled: true
        service-id: config-server
eureka:
  client:
    service-url:
      defaultZone: http://localhost:20000/eureka/
```
