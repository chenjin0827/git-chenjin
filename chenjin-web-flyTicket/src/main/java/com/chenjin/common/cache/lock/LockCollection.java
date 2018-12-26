package com.chenjin.common.cache.lock;

import java.io.PrintStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LockCollection
{
    private Map<String, IBaseLock> locks = Collections.synchronizedMap(new HashMap());
    private LockFactory lockFactory;

    public IBaseLock getLock(Class<?> c, String path)
    {
        path = c.getName() + path;
        IBaseLock lock = (IBaseLock)this.locks.get(path);
        if (lock == null) {
            synchronized (this) {
                lock = (IBaseLock)this.locks.get(path);
                if (lock == null) {
                    System.out.println(path + "获得new lock");
                    lock = this.lockFactory.newLock(path);

                    this.locks.put(path, lock);
                }
            }
        }

        return lock;
    }

    public Map<String, IBaseLock> getLocks() {
        return this.locks;
    }

    public void setLocks(Map<String, IBaseLock> locks) {
        this.locks = locks;
    }

    public LockFactory getLockFactory() {
        return this.lockFactory;
    }

    public void setLockFactory(LockFactory lockFactory) {
        this.lockFactory = lockFactory;
    }
}