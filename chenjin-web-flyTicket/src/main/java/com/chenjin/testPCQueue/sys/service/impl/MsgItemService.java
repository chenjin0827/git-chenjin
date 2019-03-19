package com.chenjin.testPCQueue.sys.service.impl;

import com.chenjin.testPCQueue.common.entity.DataGrid;
import com.chenjin.testPCQueue.common.entity.PageRequest;
import com.chenjin.testPCQueue.common.framework.service.BaseService;
import com.chenjin.testPCQueue.sys.dao.IMsgItemDao;
import com.chenjin.testPCQueue.sys.entity.MsgItem;
import com.chenjin.testPCQueue.sys.service.IMsgItemService;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class MsgItemService extends BaseService<MsgItem, Long>
        implements IMsgItemService
{
    private IMsgItemDao msgItemDao;

    @Resource
    public void setIMsgItemDao(IMsgItemDao msgItemDao)
    {
        this.msgItemDao = msgItemDao;
        super.setBaseDao(msgItemDao);
    }

    public DataGrid<MsgItem> getMsglist(String projectCode, MsgItem mrl, PageRequest pageable)
    {
        return this.msgItemDao.getMsglist(mrl, pageable);
    }
}