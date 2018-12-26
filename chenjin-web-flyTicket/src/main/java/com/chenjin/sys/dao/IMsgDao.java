package com.chenjin.sys.dao;

import com.chenjin.common.entity.DataGrid;
import com.chenjin.common.entity.PageRequest;
import com.chenjin.common.framework.dao.IBaseDao;
import com.chenjin.sys.entity.Msg;

public abstract interface IMsgDao extends IBaseDao<Msg, Long>
{
    public abstract DataGrid<Msg> getDraftMsg(Msg paramMsg, PageRequest paramPageRequest);
}