package com.chenjin.sys.service;

import com.chenjin.common.entity.DataGrid;
import com.chenjin.common.entity.PageRequest;
import com.chenjin.common.framework.annotation.ProjectCodeFlag;
import com.chenjin.common.framework.service.IBaseService;
import com.chenjin.sys.entity.Group;

public abstract interface IGroupService extends IBaseService<Group, Long>
{
    public abstract DataGrid<Group> queryByDept(@ProjectCodeFlag String paramString, PageRequest paramPageRequest, Group paramGroup);
}