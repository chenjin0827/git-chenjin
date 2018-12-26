package com.chenjin.sys.service.impl;

import com.chenjin.common.framework.service.BaseService;
import com.chenjin.sys.dao.IMenuDao;
import com.chenjin.sys.entity.Menu;
import com.chenjin.sys.service.IMenuService;
import java.io.PrintStream;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class MenuService extends BaseService<Menu, Long>
        implements IMenuService
{
    private IMenuDao menuDao;

    public IMenuDao getMenuDao()
    {
        return this.menuDao;
    }
    @Resource
    public void setMenuDao(IMenuDao menuDao) {
        this.menuDao = menuDao;
        super.setBaseDao(menuDao);
    }

    public Integer getNewSort(String projectCode, Long parentId)
    {
        if (parentId == null)
            return Integer.valueOf(0);
        List list = this.menuDao.getChlidList(parentId, "F");
        if ((list == null) || (list.size() == 0))
            return Integer.valueOf(0);
        Menu menu = (Menu)list.get(list.size() - 1);
        Integer maxSort = menu.getSort();
        maxSort = Integer.valueOf(maxSort == null ? 0 : maxSort.intValue() + 1);
        return maxSort;
    }

    public Menu findByOrgTypeAndParentId(String projectCode, Menu m)
    {
        return this.menuDao.findByOrgTypeAndParentId(m);
    }

    @Transactional
    public void drag(String projectCode, Long targetId, Long sourceId, String point)
    {
        if (point == null) {
            return;
        }
        Menu target = (Menu)this.menuDao.getById(targetId);
        Menu source = (Menu)this.menuDao.getById(sourceId);

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
                moveNode(target.getParentId(), Integer.valueOf(target.getSort().intValue() + 1), Integer.valueOf(999999), 1, source);
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
            this.menuDao.update(source);
        }
    }

    private void moveNode(Long parentId, Integer startSort, Integer endSort, int step, Menu source)
    {
        System.out.println("startSort:" + startSort);
        System.out.println("endSort:" + endSort);
        List<Menu> list = this.menuDao.getChlidList(parentId, "F");
        if ((list == null) || (list.size() == 0)) {
            return;
        }
        for (Menu resource : list) {
            Integer sort = resource.getSort();
            if ((sort == null) ||
                    (sort.intValue() < startSort.intValue()) || (sort.intValue() > endSort.intValue())) continue;
            resource.setSort(Integer.valueOf(sort.intValue() + step));
            this.menuDao.update(resource);
        }

        if (step > 0) {
            source.setSort(startSort);
            source.setParentId(parentId);
            this.menuDao.update(source);
        }
        if (step < 0) {
            source.setSort(endSort);
            source.setParentId(parentId);
            this.menuDao.update(source);
        }
    }

    private boolean sameParent(Menu target, Menu source)
    {
        if ((target.getParentId() != null) && (target.getParentId().equals(target.getId())))
            target.setParentId(null);
        if ((source.getParentId() != null) && (source.getParentId().equals(source.getId()))) {
            source.setParentId(null);
        }
        return target.getParentId() == source.getParentId();
    }
}