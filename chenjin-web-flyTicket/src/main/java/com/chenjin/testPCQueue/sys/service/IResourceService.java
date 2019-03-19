package com.chenjin.testPCQueue.sys.service;

import com.chenjin.testPCQueue.common.framework.annotation.ProjectCodeFlag;
import com.chenjin.testPCQueue.common.framework.service.IBaseService;
import com.chenjin.testPCQueue.sys.entity.Resource;

import java.util.List;
import java.util.Map;

public abstract interface IResourceService extends IBaseService<Resource, Long>
{
    public abstract List<Resource> getChlidList(@ProjectCodeFlag String paramString1, Long paramLong, String paramString2);

    public abstract List<Resource> getListByType(@ProjectCodeFlag String paramString1, String paramString2);

    public abstract List<Map<String, String>> getAllForShiro(@ProjectCodeFlag String paramString);

    public abstract void drag(@ProjectCodeFlag String paramString1, Long paramLong1, Long paramLong2, String paramString2);

    public abstract Integer getNewSort(@ProjectCodeFlag String paramString, Long paramLong);

    public abstract void deleteResource(@ProjectCodeFlag String paramString, Long paramLong);
}