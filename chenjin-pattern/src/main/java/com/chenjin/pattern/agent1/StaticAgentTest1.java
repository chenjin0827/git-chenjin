package com.chenjin.pattern.agent1;

/**
 * 静态代理测试类
 * 静态代理缺点，必须自己写代理类，
 */
public class StaticAgentTest1 {
    public static void main(String[] args) {
        XiaoMing xiaoMing = new XiaoMing();
        StaticProxy staticProxy = new StaticProxy(xiaoMing);
        staticProxy.buy();
    }

}
