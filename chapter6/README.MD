chapter6 - 配置中心 Spring Cloud Config
---


## 准备工作

1. 创建一个仓库
    
    ```
    https://github.com/hanhuoer/spring-cloud-sample-config
    ```
    
    模拟线上环境, 分别创建以下文件并填入内容
    - config-client-dev.yml
        * 内容
            ```yml
            scoder:
              hello: dev
            ```
    - config-client-test.yml
        * 内容
            ```yml
            scoder:
              hello: test
            ```
    - config-client-prod.yml
        * 内容
            ```yml
            scoder:
              hello: prod
            ```

## Config Server 配置服务中心    

1. 创建 Config Server

    加入以下依赖
    
    ```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-config-server</artifactId>
    </dependency>
    ```

2. 配置

    - 启动类加入 `@EnableConfigServer` 注解, 以表明自己是配置服务

    - yml 配置
    
        如果是公共仓库, 不需要填写 `username` `password`

        ```yml
        spring:
          application:
            name: config-server
          cloud:
            config:
              server:
                git:
                  uri: https://github.com/hanhuoer/spring-cloud-sample-config
                  search-paths: repo
                  username:
                  password:
              label: master
        server:
          port: 20040
        ```


3. 测试

    启动 `ConfigServerApplication` 项目

    试一试访问 `config-client-test.yml` 的配置<br/> 
    打开地址 `http://localhost:20040/config-client/test`
    
        ```json
        {
            "name": "config-client",
            "profiles": [
                "test"
            ],
            "label": null,
            "version": "00311c7dc1775627d3f7b754990e8b32f92b91f6",
            "state": null,
            "propertySources": [
                {
                    "name": "https://github.com/hanhuoer/spring-cloud-sample-config/repo/config-client-test.yml",
                    "source": {
                        "scoder.hello": "test"
                    }
                }
            ]
        }
        ```

    如果想要看 `config-client-dev.yml` 这个文件<br>
    可以访问 `http://localhost:20040/config-client-dev.yml`
    
        scoder:
          hello: dev
    
    规则参考:
    - /{application}/{profile}[/{label}]
    - /{application}-{profile}.yml
    - /{label}/{application}-{profile}.yml
    - /{application}-{profile}.properties
    - /{label}/{application}-{profile}.properties

## Config Client

1. 创建 config-client

2. 引入依赖

    ```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-config</artifactId>
    </dependency>
    ```

3. 启动
    
    启动类只需要一个 `@SpringBootApplication` 注解

    配置文件要用 `bootstrap.properties` 

4. 测试
    
    启动项目访问 `http://localhost:20050/info` 
    
    更新配置版本的时候, 会发现, 这个地址并不能同步最新的配置
    
    解决方案:
    
    开启监控功能, 引入依赖
    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    ```
    另外在接口处开启 `@RefreshScope` 注解 (1.5以上版本还需要在 yml 中做些配置)
    
    访问 `http://localhost:20050/actuator/refresh` 同步配置
    
    *另外, 可以使用仓库的 `Webhook` 功能, 使得 push 操作后向某一接口发送请求, 达到自动同步配置的目的*
    *Spring Cloud Bus 消息总线通知配置信息的变化*
    



## 遇到的问题

1. Spring Cloud Config client 配置文件为什么要用 `bootstrap` 命名?

    这是由spring boot的加载属性文件的优先级决定的，
    你想要在加载属性之前去 spring cloud config server 上取配置文件，
    那 spring cloud config 相关配置就是需要最先加载的，
    而 bootstrap.properties 的加载是先于 application.properties 的，
    所以 config client 要配置 config 的相关配置就只能写到 bootstrap.properties 里了