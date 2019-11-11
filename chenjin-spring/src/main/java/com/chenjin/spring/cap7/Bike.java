package com.chenjin.spring.cap7;

public class Bike {

    public Bike() {
        System.out.println(this.getClass().getSimpleName()+"构造方法创建。。。");
    }
    public void init(){
        System.out.println(this.getClass().getSimpleName()+"初始化了。。。");
    }
    public void destroy(){
        System.out.println(this.getClass().getSimpleName()+"销毁了。。。");
    }
}
