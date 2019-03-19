package com.chenjin.testPCQueue.sys.dao;

import com.chenjin.testPCQueue.common.entity.DataGrid;
import com.chenjin.testPCQueue.common.entity.PageRequest;
import com.chenjin.testPCQueue.common.framework.dao.IBaseDao;
import com.chenjin.testPCQueue.sys.entity.Msg;

public abstract interface IMsgDao extends IBaseDao<Msg, Long>
{
    public abstract DataGrid<Msg> getDraftMsg(Msg paramMsg, PageRequest paramPageRequest);
}