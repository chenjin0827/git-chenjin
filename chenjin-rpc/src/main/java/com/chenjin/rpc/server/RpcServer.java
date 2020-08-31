package com.chenjin.rpc.server;

import com.chenjin.rpc.RpcService;
import com.chenjin.rpc.registry.ServiceRegistry;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用 Netty 可实现一个支持 NIO 的 RPC 服务器，需要使用ServiceRegistry注册服务地址
 */
public class RpcServer implements ApplicationContextAware, InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(RpcServer.class);
    private String serverAddress;
    private ServiceRegistry serviceRegistry;
    //存放接口名与服务对象之间的映射关系
    Map<String, Object> handlerMap = new HashMap();

    public RpcServer(String serverAddress, ServiceRegistry serviceRegistry) {
        this.serverAddress = serverAddress;
        this.serviceRegistry = serviceRegistry;
    }

    public RpcServer(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 获取所有带有 RpcService 注解的 Spring Bean
        Map<String, Object> serviceBeanMap = applicationContext.getBeansWithAnnotation(RpcService.class);
        boolean notEmpty = MapUtils.isNotEmpty(serviceBeanMap);
        if (notEmpty) {
            Collection<Object> values = serviceBeanMap.values();
            for (Object o : values) {
                String name = o.getClass().getAnnotation(RpcService.class).value().getName();
                handlerMap.put(name, o);
            }
            LOGGER.info("所有rpcService绑定完成");
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }


}
