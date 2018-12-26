package com.chenjin.sys.dao.impl;

import com.chenjin.common.framework.dao.BaseDao;
import com.chenjin.sys.dao.IPermissionDao;
import com.chenjin.sys.entity.Permission;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class PermissionDao extends BaseDao<Permission, Long>
        implements IPermissionDao
{
    public List<Permission> findByGroup(Long deptId)
    {
        String hql = "from Permission t where group.id=? ";
        return super.listByHql(hql, null, new Object[] { deptId });
    }

    public List<Map<String, Object>> getMyPermission(Long id)
    {
        String sql = "select DISTINCT b.resourceId as RESOURCEID from  t_sys_group_user a  left join t_sys_permission b on a.groupId = b.groupId  left join t_sys_group d on d.id = a.groupId  left join t_sys_user e on e.id = a.userId where a.userId =? and e.organizationId=d.organizationId ";

        return super.listBySql(sql, null, Map.class, new Object[] { id });
    }

    public List<Permission> listByResource(Long id)
    {
        String hql = "from Permission p where p.resourceId=?";
        return super.listByHql(hql, null, new Object[] { id });
    }

    public Permission findByRG(Long resourceId, Long groupId)
    {
        String hql = "from Permission p where p.resourceId=? and p.group.id=?";
        return (Permission)super.getByHql(hql, new Object[] { resourceId, groupId });
    }
}