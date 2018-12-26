package com.chenjin.sys.dao;

import com.chenjin.common.entity.DataGrid;
import com.chenjin.common.entity.PageRequest;
import com.chenjin.common.framework.dao.IBaseDao;
import com.chenjin.sys.entity.MsgItem;

public abstract interface IMsgItemDao extends IBaseDao<MsgItem, Long>
{
    public abstract DataGrid<MsgItem> getMsglist(MsgItem paramMsgItem, PageRequest paramPageRequest);
}