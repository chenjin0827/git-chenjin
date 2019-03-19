package com.chenjin.testPCQueue.common.ca;

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