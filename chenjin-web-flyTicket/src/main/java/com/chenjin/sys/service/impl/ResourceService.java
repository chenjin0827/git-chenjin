package com.chenjin.sys.service.impl;

import com.chenjin.common.framework.service.BaseService;
import com.chenjin.sys.dao.IPermissionDao;
import com.chenjin.sys.dao.IResourceDao;
import com.chenjin.sys.entity.Permission;
import com.chenjin.sys.entity.Resource;
import com.chenjin.sys.service.IResourceService;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class ResourceService extends BaseService<com.chenjin.sys.entity.Resource, Long>
        implements IResourceService
{
    private IResourceDao resourceDao;

    @javax.annotation.Resource
    private IPermissionDao permissionDao;

    @javax.annotation.Resource
    public void setResourceDao(IResourceDao resourceDao)
    {
        this.resourceDao = resourceDao;
        super.setBaseDao(resourceDao);
    }

    public List<com.chenjin.sys.entity.Resource> getChlidList(String projectCode, Long id, String type)
    {
        return this.resourceDao.getChlidList(id, type);
    }

    public List<com.chenjin.sys.entity.Resource> getListByType(String projectCode, String type)
    {
        return this.resourceDao.getListByType(type);
    }

    @Transactional
    public List<Map<String, String>> getAllForShiro(String projectCode)
    {
        return this.resourceDao.getAllForShiro();
    }

    @Transactional
    public void drag(String projectCode, Long targetId, Long sourceId, String point) {
        if (point == null) {
            return;
        }
        com.chenjin.sys.entity.Resource target = (com.chenjin.sys.entity.Resource)this.resourceDao.getById(targetId);
        com.chenjin.sys.entity.Resource source = (com.chenjin.sys.entity.Resource)this.resourceDao.getById(sourceId);

        if ((target == null) || (source == null)) {
            return;
        }
        if (point.equals("top")) {
            if (sameParent(target, source)) {
                if (target.getSort().intValue() < source.getSort().intValue())
                    moveNode(target.getParentId(), target.getSort(), Integer.valueOf(source.getSort().intValue() - 1), 1, source);
                else
                    moveNode(target.getParentId(), Integer.valueOf(source.getSort().intValue() + 1), Integer.valueOf(target.getSort().intValue() - 1), -1, source);
            }
            else
                moveNode(target.getParentId(), target.getSort(), Integer.valueOf(999999), 1, source);
        }
        else if (point.equals("bottom")) {
            if (sameParent(target, source)) {
                if (target.getSort().intValue() < source.getSort().intValue())
                    moveNode(target.getParentId(), Integer.valueOf(target.getSort().intValue() + 1), Integer.valueOf(source.getSort().intValue() - 1), 1, source);
                else
                    moveNode(target.getParentId(), Integer.valueOf(source.getSort().intValue() + 1), target.getSort(), -1, source);
            }
            else
                moveNode(target.getParentId(), target.getSort(), Integer.valueOf(999999), 1, source);
        }
        else if (point.equals("append")) {
            System.out.println("append");
            Integer sort = getNewSort(projectCode, target.getId());
            if (target.getId() == null)
                source.setParentId(source.getId());
            else {
                source.setParentId(target.getId());
            }

            source.setSort(sort);
            this.resourceDao.update(source);
        }
    }

    private void moveNode(Long parentId, Integer startSort, Integer endSort, int step, com.chenjin.sys.entity.Resource source)
    {
        System.out.println("startSort:" + startSort);
        System.out.println("endSort:" + endSort);
        List<Resource> list = this.resourceDao.getChlidList(parentId, "F");
        if ((list == null) || (list.size() == 0)) {
            return;
        }
        for (com.chenjin.sys.entity.Resource resource : list) {
            Integer sort = resource.getSort();
            if ((sort == null) ||
                    (sort.intValue() < startSort.intValue()) || (sort.intValue() > endSort.intValue())) continue;
            resource.setSort(Integer.valueOf(sort.intValue() + step));
            this.resourceDao.update(resource);
        }

        if (step > 0) {
            source.setSort(startSort);
            source.setParentId(parentId);
            this.resourceDao.update(source);
        }
        if (step < 0) {
            source.setSort(endSort);
            source.setParentId(parentId);
            this.resourceDao.update(source);
        }
    }

    private boolean sameParent(com.chenjin.sys.entity.Resource target, com.chenjin.sys.entity.Resource source)
    {
        if ((target.getParentId() != null) && (target.getParentId().equals(target.getId())))
            target.setParentId(null);
        if ((source.getParentId() != null) && (source.getParentId().equals(source.getId()))) {
            source.setParentId(null);
        }
        return target.getParentId() == source.getParentId();
    }

    public Integer getNewSort(String projectCode, Long parentId)
    {
        if (parentId == null)
            return Integer.valueOf(0);
        List list = this.resourceDao.getChlidList(parentId, "F");
        if ((list == null) || (list.size() == 0))
            return Integer.valueOf(0);
        com.chenjin.sys.entity.Resource resource = (com.chenjin.sys.entity.Resource)list.get(list.size() - 1);
        Integer maxSort = resource.getSort();
        maxSort = Integer.valueOf(maxSort == null ? 0 : maxSort.intValue() + 1);
        return maxSort;
    }

    @Transactional
    public void deleteResource(String projectCode, Long id)
    {
        List<Resource> childlist = this.resourceDao.getChlidList(id, null);

        for (com.chenjin.sys.entity.Resource resource : childlist)
        {
            List<Permission> permissions = this.permissionDao.listByResource(resource.getId());

            for (Permission permission : permissions) {
                this.permissionDao.delete(permission);
            }
            this.resourceDao.delete(resource);
        }
        com.chenjin.sys.entity.Resource r = (com.chenjin.sys.entity.Resource)this.resourceDao.getById(id);
        this.resourceDao.delete(r);
    }
}