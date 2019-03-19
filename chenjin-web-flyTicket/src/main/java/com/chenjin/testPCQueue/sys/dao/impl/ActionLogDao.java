package com.chenjin.testPCQueue.sys.dao.impl;

import com.chenjin.testPCQueue.common.framework.dao.BaseDao;
import com.chenjin.testPCQueue.sys.dao.IActionLogDao;
import com.chenjin.testPCQueue.sys.entity.ActionLog;
import org.springframework.stereotype.Repository;

@Repository
public class ActionLogDao extends BaseDao<ActionLog, Long>
        implements IActionLogDao
{
}