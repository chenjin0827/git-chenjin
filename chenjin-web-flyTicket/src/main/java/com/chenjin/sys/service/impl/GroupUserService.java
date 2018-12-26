package com.chenjin.sys.service.impl;

import com.chenjin.common.entity.DataGrid;
import com.chenjin.common.entity.PageRequest;
import com.chenjin.common.framework.service.BaseService;
import com.chenjin.sys.dao.IGroupUserDao;
import com.chenjin.sys.entity.Group;
import com.chenjin.sys.entity.GroupUser;
import com.chenjin.sys.service.IGroupUserService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class GroupUserService extends BaseService<GroupUser, Long>
        implements IGroupUserService
{
    private IGroupUserDao groupUserDao;

    @Resource
    public void setDeptUserDao(IGroupUserDao groupUserDao)
    {
        this.groupUserDao = groupUserDao;
        super.setBaseDao(groupUserDao);
    }

    public DataGrid<GroupUser> queryByDept(String projectCode, PageRequest pageable, Group group)
    {
        return this.groupUserDao.queryByDept(pageable, group);
    }

    public List<Map<String, Object>> getIndexTree(String projectCode, Long id)
    {
        return this.groupUserDao.getIndexTree(id);
    }

    public DataGrid<Map<String, Object>> groupUserPage(String projectCode, PageRequest pageablem, String orgType, Long groupId, String name)
    {
        return this.groupUserDao.groupUserPage(pageablem, orgType, groupId, name);
    }

    @Transactional(readOnly=true)
    public List<Map<String, Object>> groupUserPageSelect(String projectCode, Long id)
    {
        return this.groupUserDao.groupUserPageSelect(id);
    }

    public GroupUser findByKey(String projectCode, Long userId, Long groupId)
    {
        return this.groupUserDao.findByKey(userId, groupId);
    }
}