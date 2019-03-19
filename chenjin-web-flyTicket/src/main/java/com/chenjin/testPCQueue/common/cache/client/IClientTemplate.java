package com.chenjin.testPCQueue.common.cache.client;

import java.util.List;

public abstract interface IClientTemplate
{
    public abstract void createNode(String paramString, Object paramObject);

    public abstract void updateNode(String paramString, Object paramObject);

    public abstract void deleteNode(String paramString);

    public abstract Object getNode(String paramString);

    public abstract List<String> getChildren(String paramString);
}