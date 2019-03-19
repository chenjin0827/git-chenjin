package com.chenjin.testPCQueue.sys.service;

import com.chenjin.testPCQueue.common.entity.DataGrid;
import com.chenjin.testPCQueue.common.entity.PageRequest;
import com.chenjin.testPCQueue.common.framework.annotation.ProjectCodeFlag;
import com.chenjin.testPCQueue.common.framework.service.IBaseService;
import com.chenjin.testPCQueue.sys.entity.MsgItem;

public abstract interface IMsgItemService extends IBaseService<MsgItem, Long>
{
    public abstract DataGrid<MsgItem> getMsglist(@ProjectCodeFlag String paramString, MsgItem paramMsgItem, PageRequest paramPageRequest);
}