package com.chenjin.testPCQueue.sys.service;

import com.chenjin.testPCQueue.common.framework.annotation.ProjectCodeFlag;
import com.chenjin.testPCQueue.common.framework.service.IBaseService;
import com.chenjin.testPCQueue.sys.entity.Organization;

public abstract interface IOrganizationService extends IBaseService<Organization, Long>
{
    public abstract Organization getByOrgId(@ProjectCodeFlag String paramString, Long paramLong, Integer paramInteger);

    public abstract Organization getByOrgCode(@ProjectCodeFlag String paramString1, String paramString2, Integer paramInteger);
}