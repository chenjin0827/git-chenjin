package com.chenjin.common.cache.dao;

import com.chenjin.common.cache.client.ClientTemplate;

public class TaskLockDAO
{
    private String parentPath = "/TASKLOCK";
    private ClientTemplate clientTemplate;

    public Boolean lock(String lockPath)
    {
        String path = this.parentPath + "/" + lockPath;
        try {
            Object object = this.clientTemplate.getNode(path);
            if (object == null) {
                this.clientTemplate.createNode(path, "");
                return Boolean.valueOf(true);
            }
        } catch (Exception localException) {
        }
        return Boolean.valueOf(false);
    }

    public void unlock(String lockPath) {
        String path = this.parentPath + "/" + lockPath;
        this.clientTemplate.deleteNode(path);
    }

    public String getParentPath() {
        return this.parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    public ClientTemplate getClientTemplate() {
        return this.clientTemplate;
    }

    public void setClientTemplate(ClientTemplate clientTemplate) {
        this.clientTemplate = clientTemplate;
    }
}