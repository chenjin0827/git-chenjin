package com.chenjin.sys.dao.impl;

import com.chenjin.common.framework.dao.BaseDao;
import com.chenjin.sys.dao.IResourceDao;
import com.chenjin.sys.entity.Resource;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class ResourceDao extends BaseDao<Resource, Long>
        implements IResourceDao
{
    public List<Resource> getChlidList(Long id, String type)
    {
        String hql = "from Resource where parentId= " + id;
        if (id == null) {
            hql = "from Resource where parentId is null ";
        }
        if (type != null) {
            hql = hql + " and type='" + type + "'";
        }
        hql = hql + " order by sort";
        return super.listByHql(hql, null, new Object[0]);
    }

    public List<Resource> getListByType(String type)
    {
        String hql = "from Resource where type= ? order by sort";
        return super.listByHql(hql, null, new Object[] { type });
    }

    public List<Map<String, String>> getAllForShiro()
    {
        String sql = "select c.permCode,c.url from  t_sys_resource c  where c.permCode is not null  group by c.permCode,c.url";
        return super.listBySql(sql, null, Map.class, new Object[0]);
    }
}