package com.chenjin.sys.service;

import com.chenjin.common.entity.DataGrid;
import com.chenjin.common.entity.PageRequest;
import com.chenjin.common.framework.annotation.ProjectCodeFlag;
import com.chenjin.common.framework.service.IBaseService;
import com.chenjin.sys.entity.MsgItem;

public abstract interface IMsgItemService extends IBaseService<MsgItem, Long>
{
    public abstract DataGrid<MsgItem> getMsglist(@ProjectCodeFlag String paramString, MsgItem paramMsgItem, PageRequest paramPageRequest);
}