package com.chenjin.testPCQueue.sys.dao.impl;

import com.chenjin.testPCQueue.common.framework.dao.BaseDao;
import com.chenjin.testPCQueue.sys.dao.IOrganizationDao;
import com.chenjin.testPCQueue.sys.entity.Organization;
import org.springframework.stereotype.Repository;

@Repository
public class OrganizationDao extends BaseDao<Organization, Long>
        implements IOrganizationDao
{
    public Organization getByOrgId(Long orgId, Integer type)
    {
        String hql = "from Organization t where t.orgId=? and t.orgType=?";
        return (Organization)super.getByHql(hql, new Object[] { orgId, type });
    }

    public Organization getByOrgCode(String orgCode, Integer type)
    {
        String hql = "from Organization t where t.orgCode=? and t.orgType=?";
        return (Organization)super.getByHql(hql, new Object[] { orgCode, type });
    }
}