
package com.chenjin.testPCQueue.common.cache.lock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

public class DistributedLock implements IBaseLock {
    private InterProcessMutex lock = null;

    public DistributedLock(CuratorFramework cf, String path) {
        this.lock = new InterProcessMutex(cf, path);
    }

    public void lock() {
        try {
            this.lock.acquire();
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

    public void unlock() {
        try {
            this.lock.release();
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }
}
