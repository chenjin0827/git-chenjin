package com.chenjin.testPCQueue.sys.service.impl;

import com.chenjin.testPCQueue.common.entity.DataGrid;
import com.chenjin.testPCQueue.common.entity.PageRequest;
import com.chenjin.testPCQueue.common.framework.service.BaseService;
import com.chenjin.testPCQueue.sys.dao.IGroupDao;
import com.chenjin.testPCQueue.sys.entity.Group;
import com.chenjin.testPCQueue.sys.service.IGroupService;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class GroupService extends BaseService<Group, Long>
        implements IGroupService
{
    private IGroupDao groupDao;

    public IGroupDao getGroupDao()
    {
        return this.groupDao;
    }
    @Resource
    public void setGroupDao(IGroupDao groupDao) { this.groupDao = groupDao;
        super.setBaseDao(groupDao);
    }

    public DataGrid<Group> queryByDept(String projectCode, PageRequest pageable, Group group)
    {
        return this.groupDao.queryByDept(pageable, group);
    }
}