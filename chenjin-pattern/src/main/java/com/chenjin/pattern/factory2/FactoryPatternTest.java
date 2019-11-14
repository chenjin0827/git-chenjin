package com.chenjin.pattern.factory2;

import com.chenjin.pattern.factory1.Fruit;
import com.chenjin.pattern.factory1.StaticFactory;

/**
 * 工厂方法模式  静态工厂的升级版
 * 扩展新的品类是，不用修改已有代码
 * 做法是：将静态工厂打散，每一个水果品类，对应一个水果工厂来生产
 * 当需要扩展新的品种时，对应扩展一个水果品类的工厂就可以了
 */
public class FactoryPatternTest {
    private static FruitFactory fruitFactory;

    public static void main(String[] args) {
        //此处切换具体的水果工厂实现得到水果的切换
//        fruitFactory = new OrangeFactory();
        fruitFactory = new AppleFactory();
        cjdo();
        jydo();
        dwdo();
    }


    private static void cjdo() {
        Fruit fruit = fruitFactory.getFruit();
        fruit.eat();
    }

    private static void jydo() {
        Fruit fruit = fruitFactory.getFruit();
        fruit.eat();
    }


    //典韦吃水果
    private static void dwdo() {
        Fruit fruit = fruitFactory.getFruit();
        fruit.eat();
    }
}
