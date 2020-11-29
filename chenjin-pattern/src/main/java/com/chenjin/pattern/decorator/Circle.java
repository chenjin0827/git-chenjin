package com.chenjin.pattern.decorator;

/**
 * 负责画方
 */
public class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("Shape: Circle");
    }
}
