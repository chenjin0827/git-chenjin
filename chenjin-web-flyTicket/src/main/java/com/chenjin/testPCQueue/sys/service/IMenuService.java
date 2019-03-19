package com.chenjin.testPCQueue.sys.service;

import com.chenjin.testPCQueue.common.framework.annotation.ProjectCodeFlag;
import com.chenjin.testPCQueue.common.framework.service.IBaseService;
import com.chenjin.testPCQueue.sys.entity.Menu;

public abstract interface IMenuService extends IBaseService<Menu, Long>
{
    public abstract Integer getNewSort(@ProjectCodeFlag String paramString, Long paramLong);

    public abstract Menu findByOrgTypeAndParentId(@ProjectCodeFlag String paramString, Menu paramMenu);

    public abstract void drag(@ProjectCodeFlag String paramString1, Long paramLong1, Long paramLong2, String paramString2);
}