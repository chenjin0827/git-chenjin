package com.chenjin.testPCQueue.sys.dao.impl;

import com.chenjin.testPCQueue.common.framework.dao.BaseDao;
import com.chenjin.testPCQueue.sys.dao.IMenuDao;
import com.chenjin.testPCQueue.sys.entity.Menu;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class MenuDao extends BaseDao<Menu, Long>
        implements IMenuDao
{
    public List<Menu> getChlidList(Long id, String type)
    {
        String hql = "from Menu where parentId= " + id;
        if (id == null) {
            hql = "from Menu where parentId is null or parentId=-1 or parentId = id ";
        }

        hql = hql + " order by sort";
        return super.listByHql(hql, null, new Object[0]);
    }

    public Menu findByOrgTypeAndParentId(Menu m)
    {
        String hql = "from Menu where parentId= ? and orgType = ? and resourceId=? and sysId=? ";
        return (Menu)super.getByHql(hql, new Object[] { m.getParentId(), m.getOrgType(), m.getResourceId(), m.getSysId() });
    }
}