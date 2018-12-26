package com.chenjin.sys.dao;

import com.chenjin.common.framework.dao.IBaseDao;
import com.chenjin.sys.entity.Resource;
import java.util.List;
import java.util.Map;

public abstract interface IResourceDao extends IBaseDao<Resource, Long>
{
    public abstract List<Resource> getChlidList(Long paramLong, String paramString);

    public abstract List<Resource> getListByType(String paramString);

    public abstract List<Map<String, String>> getAllForShiro();
}