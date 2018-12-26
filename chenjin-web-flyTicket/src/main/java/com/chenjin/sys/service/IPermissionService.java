package com.chenjin.sys.service;

import com.chenjin.common.framework.annotation.ProjectCodeFlag;
import com.chenjin.common.framework.service.IBaseService;
import com.chenjin.sys.entity.Permission;
import java.util.List;
import java.util.Map;

public abstract interface IPermissionService extends IBaseService<Permission, Long>
{
    public abstract List<Map<String, Object>> getMyPermission(@ProjectCodeFlag String paramString, Long paramLong);

    public abstract List<Permission> listByResource(@ProjectCodeFlag String paramString, Long paramLong);

    public abstract Permission findByRG(@ProjectCodeFlag String paramString, Long paramLong1, Long paramLong2);
}