package com.chenjin.sys.service;

import com.chenjin.common.framework.annotation.ProjectCodeFlag;
import com.chenjin.common.framework.service.IBaseService;
import com.chenjin.sys.entity.Organization;

public abstract interface IOrganizationService extends IBaseService<Organization, Long>
{
    public abstract Organization getByOrgId(@ProjectCodeFlag String paramString, Long paramLong, Integer paramInteger);

    public abstract Organization getByOrgCode(@ProjectCodeFlag String paramString1, String paramString2, Integer paramInteger);
}