package com.chenjin.sys.dao.impl;

import com.chenjin.common.entity.PageRequest;
import com.chenjin.common.framework.dao.BaseDao;
import com.chenjin.sys.dao.IAttributeDao;
import com.chenjin.sys.entity.Attribute;
import org.springframework.stereotype.Repository;

@Repository("attributeDao")
public class AttributeDao extends BaseDao<Attribute, Long>
        implements IAttributeDao
{
    public Attribute queryByAttributeNo(PageRequest pageable, String attributeNo)
    {
        String hql = "from Attribute where attributeNo=? ";
        return (Attribute)super.getByHql(hql, new Object[] { attributeNo });
    }
}