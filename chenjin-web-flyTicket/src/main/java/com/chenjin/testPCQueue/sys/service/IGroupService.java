package com.chenjin.testPCQueue.sys.service;

import com.chenjin.testPCQueue.common.entity.DataGrid;
import com.chenjin.testPCQueue.common.entity.PageRequest;
import com.chenjin.testPCQueue.common.framework.annotation.ProjectCodeFlag;
import com.chenjin.testPCQueue.common.framework.service.IBaseService;
import com.chenjin.testPCQueue.sys.entity.Group;

public abstract interface IGroupService extends IBaseService<Group, Long>
{
    public abstract DataGrid<Group> queryByDept(@ProjectCodeFlag String paramString, PageRequest paramPageRequest, Group paramGroup);
}