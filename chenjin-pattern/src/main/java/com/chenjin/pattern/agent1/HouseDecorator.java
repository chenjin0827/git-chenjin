package com.chenjin.pattern.agent1;

/**
 * 房子装修的装饰类  如果涉及不同的装修方式可以将此类设置为抽象类，不同的类都来继承重写相应自己的方法啊
 */
public class HouseDecorator implements House{
    private House house;
    public HouseDecorator(House house) {
        this.house=house;
    }

    @Override
    public void buy() {
        setColor();
        house.buy();
    }

    private void setColor() {
        System.out.println("买房送装修，刷墙！");
    }

    public static void main(String[] args) {
        HouseDecorator houseDecorator = new HouseDecorator(new XiaoMing());
        houseDecorator.buy();
        HouseDecorator houseDecorator2 = new HouseDecorator(new XiaoHuang());
        houseDecorator2.buy();
    }
}
