package com.chenjin.rpc;

/**
 * 该实现类放在服务端 jar 包中，该 jar 包还提供了一些服务端的配置文件与启动服务的引导程序。
 */
@RpcService(value = HelloServiceImpl.class)
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "Hello!  " + name;
    }
}
