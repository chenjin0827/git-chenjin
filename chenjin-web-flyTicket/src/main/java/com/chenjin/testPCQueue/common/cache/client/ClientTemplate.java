package com.chenjin.testPCQueue.common.cache.client;

import java.util.List;

public abstract class ClientTemplate
        implements IClientTemplate
{
    public void createNode(String path, Object object)
    {
    }

    public void updateNode(String path, Object object)
    {
    }

    public void deleteNode(String path)
    {
    }

    public Object getNode(String path)
    {
        return null;
    }

    public List<String> getChildren(String path)
    {
        return null;
    }

    public abstract void createParentNode(String paramString);
}