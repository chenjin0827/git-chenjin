package com.chenjin.pattern.agent1;

/**
 * 静态代理类
 */
public class StaticProxy implements House{
    private XiaoMing xiaoMing;
    public StaticProxy(XiaoMing xiaoMing) {
        this.xiaoMing=xiaoMing;
    }

    @Override
    public void buy() {
        System.out.println("我是中介，开始监听买房");
        xiaoMing.buy();
        System.out.println("我是中介，结束监听买房");
    }
}
