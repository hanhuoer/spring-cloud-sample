chapter4 - 断路器 Hystrix
---

Hystrix

**注意**, Feign 默认是没有开启 Hystrix 的

```yml
feign:
  hystrix:
    enabled: true
```


1. 启动 `EurekaServerApplication`
2. 启动 `ServiceFeignApplication`
3. 启动 `ServiceRibbonApplication`

访问 `http://localhost:20001/hi?name=fallback` 