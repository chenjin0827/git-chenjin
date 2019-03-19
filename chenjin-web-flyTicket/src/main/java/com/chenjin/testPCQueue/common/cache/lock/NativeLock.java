package com.chenjin.testPCQueue.common.cache.lock;

import java.util.concurrent.locks.ReentrantLock;

public class NativeLock
        implements IBaseLock
{
    private ReentrantLock lock = null;

    public NativeLock() {
        this.lock = new ReentrantLock();
    }

    public void lock()
    {
        this.lock.lock();
    }

    public void unlock()
    {
        this.lock.unlock();
    }
}