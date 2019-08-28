chapter2 - 服务调用
---

ribbon + template

负载均衡

```java
@Bean
public IRule ribbonRule() {
    // 负载均衡规则，改为随机
    /**
     * ribbon的策略主要有以下几种:
        com.netflix.loadbalancer.RandomRule #配置规则 随机(feign默认开启)
        com.netflix.loadbalancer.RoundRobinRule #配置规则 轮询
        com.netflix.loadbalancer.RetryRule #配置规则 重试
        com.netflix.loadbalancer.WeightedResponseTimeRule #配置规则 响应时间权重
        com.netflix.loadbalancer.BestAvailableRule #配置规则 最空闲连接策略
     */
    return new RandomRule();
}
```