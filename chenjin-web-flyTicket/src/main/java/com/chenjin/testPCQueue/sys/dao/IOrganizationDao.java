package com.chenjin.testPCQueue.sys.dao;

import com.chenjin.testPCQueue.common.framework.dao.IBaseDao;
import com.chenjin.testPCQueue.sys.entity.Organization;

public abstract interface IOrganizationDao extends IBaseDao<Organization, Long>
{
    public abstract Organization getByOrgId(Long paramLong, Integer paramInteger);

    public abstract Organization getByOrgCode(String paramString, Integer paramInteger);
}