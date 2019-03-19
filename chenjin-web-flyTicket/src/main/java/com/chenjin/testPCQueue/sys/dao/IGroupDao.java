package com.chenjin.testPCQueue.sys.dao;

import com.chenjin.testPCQueue.common.entity.DataGrid;
import com.chenjin.testPCQueue.common.entity.PageRequest;
import com.chenjin.testPCQueue.common.framework.dao.IBaseDao;
import com.chenjin.testPCQueue.sys.entity.Group;

public abstract interface IGroupDao extends IBaseDao<Group, Long>
{
    public abstract DataGrid<Group> queryByDept(PageRequest paramPageRequest, Group paramGroup);
}