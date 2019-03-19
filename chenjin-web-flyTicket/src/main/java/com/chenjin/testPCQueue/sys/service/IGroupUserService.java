package com.chenjin.testPCQueue.sys.service;

import com.chenjin.testPCQueue.common.entity.DataGrid;
import com.chenjin.testPCQueue.common.entity.PageRequest;
import com.chenjin.testPCQueue.common.framework.annotation.ProjectCodeFlag;
import com.chenjin.testPCQueue.common.framework.service.IBaseService;
import com.chenjin.testPCQueue.sys.entity.Group;
import com.chenjin.testPCQueue.sys.entity.GroupUser;

import java.util.List;
import java.util.Map;

public abstract interface IGroupUserService extends IBaseService<GroupUser, Long>
{
    public abstract DataGrid<GroupUser> queryByDept(@ProjectCodeFlag String paramString, PageRequest paramPageRequest, Group paramGroup);

    public abstract List<Map<String, Object>> getIndexTree(@ProjectCodeFlag String paramString, Long paramLong);

    public abstract DataGrid<Map<String, Object>> groupUserPage(@ProjectCodeFlag String paramString1, PageRequest paramPageRequest, String paramString2, Long paramLong, String paramString3);

    public abstract List<Map<String, Object>> groupUserPageSelect(@ProjectCodeFlag String paramString, Long paramLong);

    public abstract GroupUser findByKey(@ProjectCodeFlag String paramString, Long paramLong1, Long paramLong2);
}