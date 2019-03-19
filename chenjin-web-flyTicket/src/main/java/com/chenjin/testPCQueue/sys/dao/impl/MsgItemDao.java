package com.chenjin.testPCQueue.sys.dao.impl;

import com.chenjin.testPCQueue.common.entity.DataGrid;
import com.chenjin.testPCQueue.common.entity.PageRequest;
import com.chenjin.testPCQueue.common.framework.dao.BaseDao;
import com.chenjin.testPCQueue.sys.dao.IMsgItemDao;
import com.chenjin.testPCQueue.sys.entity.MsgItem;
import org.springframework.stereotype.Repository;

@Repository
public class MsgItemDao extends BaseDao<MsgItem, Long>
        implements IMsgItemDao
{
    public DataGrid<MsgItem> getMsglist(MsgItem mrl, PageRequest pageable)
    {
        String hql = "from MsgRecvList t where t.sender=? and t.userId=? and t.msgHub.title like ? order by t.msgHub.modifyDate desc, t.msgHub.createDate desc";
        return super.query(hql, pageable, new Object[] { mrl.getSender(), mrl.getUserId(), "%" + mrl.getMsg().getTitle() + "%" });
    }
}