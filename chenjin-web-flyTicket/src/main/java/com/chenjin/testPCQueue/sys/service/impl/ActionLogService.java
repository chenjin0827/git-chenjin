package com.chenjin.testPCQueue.sys.service.impl;

import com.chenjin.testPCQueue.common.framework.service.BaseService;
import com.chenjin.testPCQueue.sys.dao.IActionLogDao;
import com.chenjin.testPCQueue.sys.entity.ActionLog;
import com.chenjin.testPCQueue.sys.service.IActionLogService;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class ActionLogService extends BaseService<ActionLog, Long>
        implements IActionLogService
{
    private IActionLogDao actionLogDao;

    public IActionLogDao getActionLogDao()
    {
        return this.actionLogDao;
    }
    @Resource
    public void setActionLogDao(IActionLogDao actionLogDao) { this.actionLogDao = actionLogDao;
        super.setBaseDao(actionLogDao);
    }
}