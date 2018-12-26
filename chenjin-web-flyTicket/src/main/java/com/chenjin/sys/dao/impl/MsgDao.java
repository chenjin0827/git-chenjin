package com.chenjin.sys.dao.impl;

import com.chenjin.common.entity.DataGrid;
import com.chenjin.common.entity.PageRequest;
import com.chenjin.common.framework.dao.BaseDao;
import com.chenjin.sys.dao.IMsgDao;
import com.chenjin.sys.entity.Msg;
import java.io.PrintStream;
import org.springframework.stereotype.Repository;

@Repository
public class MsgDao extends BaseDao<Msg, Long>
        implements IMsgDao
{
    public DataGrid<Msg> getDraftMsg(Msg mrl, PageRequest pageable)
    {
        System.out.println("mrl.getOwner() = " + mrl.getTitle());
        String hql = "from MsgHub t where t.ids='00' and t.ownerId=? and t.title like ? order by t.createDate desc";
        return super.query(hql, pageable, new Object[] { mrl.getOwnerId(), "%" + mrl.getTitle() + "%" });
    }
}