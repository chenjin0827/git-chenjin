package com.chenjin.pattern.factory1;

/**
 * 简单工厂模式
 */

public class StaticFactory {
    public static final int TYPE_APPLE = 1;
    public static final int TYPE_ORANGE = 2;

    /**
     * 静态工厂 扩建的时候需要增加类型入参，内部方法也要增加一个
     *
     * @param type
     * @return
     */
    public static Fruit getFruit(int type) {
        if (TYPE_APPLE == type) {
            return new Apple();
        }
        if (TYPE_ORANGE == type) {
            return new Orange();
        }
        return null;
    }

    /**
     * 多方法工厂  扩建的时候需要增加一个工厂方法就可以了
     */
    public static Fruit getApple() {
        return new Apple();
    }

    public static Fruit getOrange() {
        return new Orange();
    }


}
