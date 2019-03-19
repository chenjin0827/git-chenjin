package com.chenjin.testPCQueue.sys.service;

import com.chenjin.testPCQueue.common.framework.annotation.ProjectCodeFlag;
import com.chenjin.testPCQueue.common.framework.service.IBaseService;
import com.chenjin.testPCQueue.sys.entity.Permission;

import java.util.List;
import java.util.Map;

public abstract interface IPermissionService extends IBaseService<Permission, Long>
{
    public abstract List<Map<String, Object>> getMyPermission(@ProjectCodeFlag String paramString, Long paramLong);

    public abstract List<Permission> listByResource(@ProjectCodeFlag String paramString, Long paramLong);

    public abstract Permission findByRG(@ProjectCodeFlag String paramString, Long paramLong1, Long paramLong2);
}