package com.king.distributedSession.jetty;

import javax.servlet.*;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JettyDistributedSessionFilter extends DistributedSessionFilter {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);

        // 实例化Jetty容器下的Session管理器
        sessionManager = new JettyDistributedSessionManager(conf);

        try {
            sessionManager.start(); // 启动初始化

            //创建组节点
            ZooKeeperHelper.createGroupNode();
            log.debug("DistributedSessionFilter.init completed.");
        } catch (Exception e) {
            log.error("distributedSessionFilter init ", e);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // Jetty容器的Request对象包装器，用于重写Session的相关操作
        JettyRequestWrapper req = new JettyRequestWrapper(request, sessionManager);
        chain.doFilter(req, response);
    }
}