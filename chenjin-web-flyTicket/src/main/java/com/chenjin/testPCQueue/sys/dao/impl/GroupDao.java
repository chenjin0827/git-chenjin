package com.chenjin.testPCQueue.sys.dao.impl;

import com.chenjin.testPCQueue.common.entity.DataGrid;
import com.chenjin.testPCQueue.common.entity.PageRequest;
import com.chenjin.testPCQueue.common.framework.dao.BaseDao;
import com.chenjin.testPCQueue.sys.dao.IGroupDao;
import com.chenjin.testPCQueue.sys.entity.Group;
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