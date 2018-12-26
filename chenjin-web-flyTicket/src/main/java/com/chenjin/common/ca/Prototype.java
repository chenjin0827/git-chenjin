package com.chenjin.common.ca;

public class Prototype
        implements Cloneable
{
    public Object cloneA()
            throws CloneNotSupportedException
    {
        Prototype proto = (Prototype)super.clone();
        return proto;
    }
}