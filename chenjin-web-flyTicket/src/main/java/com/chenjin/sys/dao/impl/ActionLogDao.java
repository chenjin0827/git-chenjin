package com.chenjin.sys.dao.impl;

import com.chenjin.common.framework.dao.BaseDao;
import com.chenjin.sys.dao.IActionLogDao;
import com.chenjin.sys.entity.ActionLog;
import org.springframework.stereotype.Repository;

@Repository
public class ActionLogDao extends BaseDao<ActionLog, Long>
        implements IActionLogDao
{
}