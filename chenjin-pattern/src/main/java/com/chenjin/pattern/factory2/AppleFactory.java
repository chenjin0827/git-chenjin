package com.chenjin.pattern.factory2;

import com.chenjin.pattern.factory1.Apple;
import com.chenjin.pattern.factory1.Fruit;

public class AppleFactory implements FruitFactory {
    @Override
    public Fruit getFruit() {
        return new Apple();
    }
}
