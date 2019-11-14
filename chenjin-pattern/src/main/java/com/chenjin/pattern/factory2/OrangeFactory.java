package com.chenjin.pattern.factory2;

import com.chenjin.pattern.factory1.Fruit;
import com.chenjin.pattern.factory1.Orange;

public class OrangeFactory implements FruitFactory {
    @Override
    public Fruit getFruit() {
        return new Orange();
    }
}
