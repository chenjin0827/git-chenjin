package com.chenjin.sys.dao.impl;

import com.chenjin.common.entity.DataGrid;
import com.chenjin.common.entity.PageRequest;
import com.chenjin.common.framework.dao.BaseDao;
import com.chenjin.sys.dao.IGroupDao;
import com.chenjin.sys.entity.Group;
import org.springframework.stereotype.Repository;

@Repository
public class GroupDao extends BaseDao<Group, Long>
        implements IGroupDao
{
    public DataGrid<Group> queryByDept(PageRequest pageable, Group group)
    {
        return null;
    }
}