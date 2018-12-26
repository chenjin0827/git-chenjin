package com.chenjin.common.cache.dao;

import com.chenjin.common.cache.client.ClientTemplate;

public class VerifyCodeDAO
{
    private String parentPath = "/VERIFYCODE";
    private ClientTemplate clientTemplate;

    public void save(String subPath, String verifyCode)
    {
        String path = this.parentPath + '/' + subPath;

        Object object = this.clientTemplate.getNode(path);
        if (object == null)
            this.clientTemplate.createNode(path, verifyCode);
        else
            this.clientTemplate.updateNode(path, verifyCode);
    }

    public String get(String subPath)
    {
        String path = this.parentPath + '/' + subPath;

        Object object = this.clientTemplate.getNode(path);
        if (object != null) {
            return (String)object;
        }

        return null;
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