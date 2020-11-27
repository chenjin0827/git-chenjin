package com.chenjin.pattern.decorator;

public class DecoratorPatternDemo {
    public static void main(String[] args) {
        Circle circle = new Circle();
        circle.draw();
        RedShapeDecorator circleRedShapeDecorator = new RedShapeDecorator(new Circle());
        circleRedShapeDecorator.draw();
        RedShapeDecorator rectangleRedShapeDecorator = new RedShapeDecorator(new Rectangle());
        rectangleRedShapeDecorator.draw();
    }
}
