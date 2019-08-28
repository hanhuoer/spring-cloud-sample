chapter5 - 路由网关 zuul
---


1. 开启路由网关

    ```
    @EnableZuulProxy
    ```

2. 配置路由

    ```yml
    zuul:
      routes:
        api-a:
          path: /api-a/**
          service-id: service-ribbon
        api-b:
          path: /api-b/**
          service-id: service-feign
    ```

3. 测试

    通过刚才配置的路由服务访问

    > http://localhost:20030/api-a/hi?name=ribbon
    
    > http://localhost:20030/api-b/hi?name=feign

## 开启过滤规则


1. 加入自定义过滤规则
    
    ```java
    @Component
    public class CustomFilter extends ZuulFilter {
    
        private Logger log = LoggerFactory.getLogger(CustomFilter.class);
    
        /**
         * 决定当前过滤器何时工作
         * <p>
         * pre：路由之前
         * routing：路由之时
         * post： 路由之后
         * error：发送错误调用
         *
         * @return String
         */
        @Override
        public String filterType() {
            return "pre";
        }
    
        /**
         * 过滤器的顺序
         *
         * @return int
         */
        @Override
        public int filterOrder() {
            return 0;
        }
    
        /**
         * 决定是否开启过滤, 这里设置为 true, 始终过滤
         *
         * @return boolean
         */
        @Override
        public boolean shouldFilter() {
            return true;
        }
    
        /**
         * 过滤规则
         *
         * @return Object
         * @throws ZuulException
         */
        @Override
        public Object run() throws ZuulException {
            RequestContext ctx = RequestContext.getCurrentContext();
            HttpServletRequest request = ctx.getRequest();
            log.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
            Object accessToken = request.getParameter("token");
            if (accessToken == null) {
                log.warn("token is empty");
                ctx.setSendZuulResponse(false);
                ctx.setResponseStatusCode(401);
                try {
                    ctx.getResponse().getWriter().write("token is empty");
                } catch (Exception e) {
                }
    
                return null;
            }
            log.info("ok");
            return null;
        }
    }
    ```

2. 测试
    
    > http://localhost:20030/api-b/hi?name=forezp
    
        token is empty
     
    > http://localhost:20030/api-b/hi?name=forezp&token=1
    
        正常