package com.chenjin.sys.service.impl;

import com.chenjin.common.entity.DataGrid;
import com.chenjin.common.entity.PageRequest;
import com.chenjin.common.framework.service.BaseService;
import com.chenjin.sys.dao.IMsgItemDao;
import com.chenjin.sys.entity.MsgItem;
import com.chenjin.sys.service.IMsgItemService;
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