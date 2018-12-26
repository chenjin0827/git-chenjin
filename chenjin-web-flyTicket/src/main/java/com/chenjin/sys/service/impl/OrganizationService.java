package com.chenjin.sys.service.impl;

import com.chenjin.common.framework.service.BaseService;
import com.chenjin.sys.dao.IOrganizationDao;
import com.chenjin.sys.entity.Organization;
import com.chenjin.sys.service.IOrganizationService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class OrganizationService extends BaseService<Organization, Long>
        implements IOrganizationService
{
    private IOrganizationDao organizationDao;

    public IOrganizationDao getOrganizationDao()
    {
        return this.organizationDao;
    }
    @Resource
    public void setIOrganizationDao(IOrganizationDao organizationDao) { this.organizationDao = organizationDao;
        super.setBaseDao(organizationDao);
    }

    public Organization getByOrgId(String projectCode, Long orgId, Integer type)
    {
        return this.organizationDao.getByOrgId(orgId, type);
    }

    public Organization getByOrgCode(String projectCode, String orgCode, Integer type)
    {
        return this.organizationDao.getByOrgCode(orgCode, type);
    }
}