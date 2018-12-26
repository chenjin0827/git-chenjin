package com.chenjin.sys.dao;

import com.chenjin.common.framework.dao.IBaseDao;
import com.chenjin.sys.entity.Organization;

public abstract interface IOrganizationDao extends IBaseDao<Organization, Long>
{
    public abstract Organization getByOrgId(Long paramLong, Integer paramInteger);

    public abstract Organization getByOrgCode(String paramString, Integer paramInteger);
}