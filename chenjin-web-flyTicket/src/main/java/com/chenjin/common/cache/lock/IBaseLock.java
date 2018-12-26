package com.chenjin.common.cache.lock;

public abstract interface IBaseLock
{
    public abstract void lock();

    public abstract void unlock();
}