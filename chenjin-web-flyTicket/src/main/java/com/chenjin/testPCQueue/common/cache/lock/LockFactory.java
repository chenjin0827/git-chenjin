package com.chenjin.testPCQueue.common.cache.lock;

import org.apache.curator.framework.CuratorFramework;

public class LockFactory
{
    private CuratorFramework cf;
    private String parentPath = "/MYLOCK";

    public IBaseLock newLock(String path) {
        if (this.cf == null) {
            return new NativeLock();
        }
        path = this.parentPath + '/' + path;
        return new DistributedLock(this.cf, path);
    }

    public CuratorFramework getCf() {
        return this.cf;
    }

    public void setCf(CuratorFramework cf) {
        this.cf = cf;
    }

    public String getParentPath() {
        return this.parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }
}