package com.scoder.configclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 要想更改配置使之生效, 需要引入 actuator,
 * 开启 @RefreshScope, 并访问 /actuator/refresh 刷新配置
 * 1.5 以上需要在 yml 中把 endpoints 暴露出来, 具体查看本模块的 yml 配置
 *
 * http://localhost:20050/actuator/refresh 更新配置到最新版本 - POST
 *
 * @author shaokang
 **/
@RestController
@RefreshScope
public class HelloController {

    @Value("${scoder.info}")
    private String content;

    @GetMapping("/info")
    public String info() {
        return content;
    }

}
