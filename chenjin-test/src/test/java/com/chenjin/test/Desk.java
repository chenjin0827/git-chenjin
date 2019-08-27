package com.chenjin.test;

public class Desk {
    private String name;
    private int width;

    public Desk(String name, int width) {
        this.name = name;
        this.width = width;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return "Desk{" +
                "name='" + name + '\'' +
                ", width=" + width +
                '}';
    }
}
