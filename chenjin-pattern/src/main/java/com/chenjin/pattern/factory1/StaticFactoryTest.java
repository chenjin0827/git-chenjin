package com.chenjin.pattern.factory1;

/**
 * 简单工厂模式   静态工厂
 * 把所有创建bean的动作管理起来
 * 我需要了解具体需要传进去的参数
 * 不符合单一职责原则，一个类负责了各类水果的创建
 * 缺点--->不符合开闭原则：扩展品类时，需要修改已有代码
 */
public class StaticFactoryTest {
    public static void main(String[] args) {
        cjdo();
        jydo();
        dwdo();
    }

    private static void cjdo() {
        Fruit fruit = StaticFactory.getFruit(StaticFactory.TYPE_APPLE);
        fruit.eat();
    }

    private static void jydo() {
        Fruit fruit = StaticFactory.getFruit(StaticFactory.TYPE_ORANGE);
        fruit.eat();
    }



    //典韦吃水果
    private static void dwdo() {
        Fruit fruit = StaticFactory.getOrange();
        fruit.eat();
    }


}
