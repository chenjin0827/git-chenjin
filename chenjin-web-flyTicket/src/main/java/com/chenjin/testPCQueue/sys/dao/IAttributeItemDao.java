package com.chenjin.testPCQueue.sys.dao;

import com.chenjin.testPCQueue.common.entity.DataGrid;
import com.chenjin.testPCQueue.common.entity.PageRequest;
import com.chenjin.testPCQueue.common.entity.Sort;
import com.chenjin.testPCQueue.common.framework.dao.IBaseDao;
import com.chenjin.testPCQueue.sys.entity.Attribute;
import com.chenjin.testPCQueue.sys.entity.AttributeItem;

import java.util.List;

public abstract interface IAttributeItemDao extends IBaseDao<AttributeItem, Long>
{
    public abstract DataGrid<AttributeItem> queryByAttributeItem(PageRequest paramPageRequest, Attribute paramAttribute, String paramString);

    public abstract List<AttributeItem> getItemSelect(Sort paramSort, String paramString);

    public abstract List<AttributeItem> queryByAttributeNo(Attribute paramAttribute);

    public abstract AttributeItem queryByAttrAndItemNo(String paramString1, String paramString2);

    public abstract AttributeItem queryByAttrAndField2(String paramString1, String paramString2);

    public abstract String getMaxCode(String paramString);

    public abstract List<AttributeItem> listByAttributeNo(String paramString);
}