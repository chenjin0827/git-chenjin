package com.chenjin.sys.dao;

import com.chenjin.common.entity.PageRequest;
import com.chenjin.common.framework.dao.IBaseDao;
import com.chenjin.sys.entity.Attribute;

public abstract interface IAttributeDao extends IBaseDao<Attribute, Long>
{
    public abstract Attribute queryByAttributeNo(PageRequest paramPageRequest, String paramString);
}