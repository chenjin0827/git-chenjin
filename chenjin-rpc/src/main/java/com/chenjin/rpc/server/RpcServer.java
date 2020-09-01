package com.chenjin.rpc.server;

import com.chenjin.rpc.RpcService;
import com.chenjin.rpc.coder.RpcDecoder;
import com.chenjin.rpc.coder.RpcEncoder;
import com.chenjin.rpc.handler.RpcHandler;
import com.chenjin.rpc.pojo.RpcRequest;
import com.chenjin.rpc.pojo.RpcResponse;
import com.chenjin.rpc.registry.ServiceRegistry;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
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
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //将 RPC 请求进行解码（为了处理请求）
                            socketChannel.pipeline().addLast(new RpcDecoder(RpcRequest.class))
                                    // 将 RPC 响应进行编码（为了返回响应）
                                    .addLast(new RpcEncoder(RpcResponse.class))
                                    //处理rpc请求
                                    .addLast(new RpcHandler(handlerMap));

                        }
                    }).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            String[] array = serverAddress.split(":");
            String host = array[0];
            int port = Integer.parseInt(array[1]);
            ChannelFuture future = bootstrap.bind(host, port).sync();
            LOGGER.debug("server started on port {}", port);
            if (serviceRegistry != null) {
                //注册服务地址
                serviceRegistry.register(serverAddress);
            }
            //此处会存在阻塞情况
//            future.channel().closeFuture().sync();
        } finally {
//            workerGroup.shutdownGracefully();
//            bossGroup.shutdownGracefully();
        }

    }


}
