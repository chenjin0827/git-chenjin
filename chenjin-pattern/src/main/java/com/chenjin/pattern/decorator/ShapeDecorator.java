package com.chenjin.pattern.decorator;

/**
 * 抽象装饰类 实现Shape并持有一个Shape，用于传入不同的圆或方
 */
public abstract class ShapeDecorator implements Shape {
    private Shape myShape;

    public ShapeDecorator(Shape myShape) {
        this.myShape = myShape;
    }

    @Override
    public void draw() {
        myShape.draw();
    }

}
