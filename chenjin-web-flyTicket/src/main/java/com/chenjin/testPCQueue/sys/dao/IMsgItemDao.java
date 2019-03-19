package com.chenjin.testPCQueue.sys.dao;

import com.chenjin.testPCQueue.common.entity.DataGrid;
import com.chenjin.testPCQueue.common.entity.PageRequest;
import com.chenjin.testPCQueue.common.framework.dao.IBaseDao;
import com.chenjin.testPCQueue.sys.entity.MsgItem;

public abstract interface IMsgItemDao extends IBaseDao<MsgItem, Long>
{
    public abstract DataGrid<MsgItem> getMsglist(MsgItem paramMsgItem, PageRequest paramPageRequest);
}