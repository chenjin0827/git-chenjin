package com.chenjin.testPCQueue.sys.dao;

import com.chenjin.testPCQueue.common.entity.PageRequest;
import com.chenjin.testPCQueue.common.framework.dao.IBaseDao;
import com.chenjin.testPCQueue.sys.entity.Attribute;

public abstract interface IAttributeDao extends IBaseDao<Attribute, Long>
{
    public abstract Attribute queryByAttributeNo(PageRequest paramPageRequest, String paramString);
}