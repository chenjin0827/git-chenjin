package com.chenjin.testPCQueue.sys.service.impl;

import com.chenjin.testPCQueue.common.framework.service.BaseService;
import com.chenjin.testPCQueue.sys.dao.IPermissionDao;
import com.chenjin.testPCQueue.sys.entity.Permission;
import com.chenjin.testPCQueue.sys.service.IPermissionService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class PermissionService extends BaseService<Permission, Long>
        implements IPermissionService
{
    private IPermissionDao permissionDao;

    @Resource
    public void setPermissionDao(IPermissionDao permissionDao)
    {
        this.permissionDao = permissionDao;
        super.setBaseDao(permissionDao);
    }

    public List<Map<String, Object>> getMyPermission(String projectCode, Long id)
    {
        return this.permissionDao.getMyPermission(id);
    }

    public List<Permission> listByResource(String projectCode, Long id)
    {
        return this.permissionDao.listByResource(id);
    }

    public Permission findByRG(String projectCode, Long resourceId, Long groupId)
    {
        return this.permissionDao.findByRG(resourceId, groupId);
    }
}