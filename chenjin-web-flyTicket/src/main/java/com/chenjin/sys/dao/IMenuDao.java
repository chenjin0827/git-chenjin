package com.chenjin.sys.dao;

import com.chenjin.common.framework.dao.IBaseDao;
import com.chenjin.sys.entity.Menu;
import java.util.List;

public abstract interface IMenuDao extends IBaseDao<Menu, Long>
{
    public abstract List<Menu> getChlidList(Long paramLong, String paramString);

    public abstract Menu findByOrgTypeAndParentId(Menu paramMenu);
}