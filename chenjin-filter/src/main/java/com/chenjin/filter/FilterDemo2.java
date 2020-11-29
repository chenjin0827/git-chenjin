package com.chenjin.filter;

import javax.servlet.*;
import java.io.IOException;
public class FilterDemo2 implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("过滤器2初始化");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("过滤器2到达过滤点之前");
        System.out.println("Hello FilterDemo2 before");
        chain.doFilter(request, response);
        System.out.println("过滤器2到达过滤点之后");
        System.out.println("Hello FilterDemo2 after");
    }

    @Override
    public void destroy() {
        System.out.println("过滤器2销毁");
    }
}
