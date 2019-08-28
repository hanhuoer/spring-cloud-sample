package com.scoder.servicezuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author shaokang
 **/
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
