package com.king.distributedSession.jetty;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.server.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DistributedSessionFilter implements Filter {

    protected Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 参数配置
     */
//    protected Configuration conf;

    /**
     * Session管理器
     */
    protected SessionManager sessionManager;

    /**
     * 初始化参数名称
     */
    public static final String SERVERS = "servers";

    public static final String TIMEOUT = "timeout";

    public static final String POOLSIZE = "poolsize";

    /**
     * 初始化
     *
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        /*conf = new Configuration();

        String servers = filterConfig.getInitParameter(SERVERS);

        if (StringUtils.isNotBlank(servers)) {
            conf.setServers(servers);
        }

        String timeout = filterConfig.getInitParameter(TIMEOUT);

        if (StringUtils.isNotBlank(timeout)) {
            try {
                conf.setTimeout(Long.valueOf(timeout));
            } catch (NumberFormatException ex) {
                log.error("timeout parse error[" + timeout + "].");
            }
        }

        String poolSize = filterConfig.getInitParameter(POOLSIZE);

        if (StringUtils.isNotBlank(poolSize)) {
            try {
                conf.setPoolSize(Integer.valueOf(poolSize));
            } catch (NumberFormatException ex) {
                log.error("poolsize parse error[" + poolSize + "].");
            }
        }

        //初始化ZooKeeper配置参数
        ZooKeeperHelper.initialize(conf);*/
    }

    /**
     * 销毁
     *
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {
        if (sessionManager != null) {
            try {
                sessionManager.stop();
            } catch (Exception e) {
                log.error("sessionManager stop : ", e);
            }
        }

        //销毁ZooKeeper
//        ZooKeeperHelper.destroy();
        log.debug("DistributedSessionFilter.destroy completed.");
    }
}