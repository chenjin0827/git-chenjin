package com.chenjin.sys.service.impl;

import com.chenjin.common.entity.DataGrid;
import com.chenjin.common.entity.PageRequest;
import com.chenjin.common.framework.service.BaseService;
import com.chenjin.sys.dao.IGroupDao;
import com.chenjin.sys.entity.Group;
import com.chenjin.sys.service.IGroupService;
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