package com.chenjin.rpc;

public interface HelloService {
    /**
     * 该接口放在独立的客户端 jar 包中，以供应用使用。
     * @param name
     * @return
     */
    String sayHello(String name);

}
