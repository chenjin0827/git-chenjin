package com.chenjin.sys.dao;

import com.chenjin.common.framework.dao.IBaseDao;
import com.chenjin.sys.entity.Permission;
import java.util.List;
import java.util.Map;

public abstract interface IPermissionDao extends IBaseDao<Permission, Long>
{
    public abstract List<Permission> findByGroup(Long paramLong);

    public abstract List<Map<String, Object>> getMyPermission(Long paramLong);

    public abstract List<Permission> listByResource(Long paramLong);

    public abstract Permission findByRG(Long paramLong1, Long paramLong2);
}