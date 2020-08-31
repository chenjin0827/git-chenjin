package com.chenjin.rpc;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RpcBootstrap {
    /**
     * 运行RpcBootstrap类的main方法即可启动服务端
     * @param args
     */
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("spring.xml");
    }
}
