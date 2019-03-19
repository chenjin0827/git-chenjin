package com.chenjin.testPCQueue.sys.dao;

import com.chenjin.testPCQueue.common.framework.dao.IBaseDao;
import com.chenjin.testPCQueue.sys.entity.Resource;

import java.util.List;
import java.util.Map;

public abstract interface IResourceDao extends IBaseDao<Resource, Long>
{
    public abstract List<Resource> getChlidList(Long paramLong, String paramString);

    public abstract List<Resource> getListByType(String paramString);

    public abstract List<Map<String, String>> getAllForShiro();
}