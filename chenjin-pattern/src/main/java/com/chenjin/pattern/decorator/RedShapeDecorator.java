package com.chenjin.pattern.decorator;

/**
 * 继承抽象类，对原有方法增加一个设置颜色的动作
 */
public class RedShapeDecorator extends ShapeDecorator {
    public RedShapeDecorator(Shape myShape) {
        super(myShape);
    }

    @Override
    public void draw() {
        setRedColor();
        super.draw();
    }

    private void setRedColor() {
        System.out.println("设置颜色为红色！");
    }
}
