package com.chenjin.sys.dao;

import com.chenjin.common.entity.DataGrid;
import com.chenjin.common.entity.PageRequest;
import com.chenjin.common.framework.dao.IBaseDao;
import com.chenjin.sys.entity.Group;

public abstract interface IGroupDao extends IBaseDao<Group, Long>
{
    public abstract DataGrid<Group> queryByDept(PageRequest paramPageRequest, Group paramGroup);
}