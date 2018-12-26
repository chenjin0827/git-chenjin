package com.chenjin.sys.service;

import com.chenjin.common.entity.DataGrid;
import com.chenjin.common.entity.PageRequest;
import com.chenjin.common.entity.Sort;
import com.chenjin.common.framework.annotation.ProjectCodeFlag;
import com.chenjin.common.framework.service.IBaseService;
import com.chenjin.sys.entity.Attribute;
import com.chenjin.sys.entity.AttributeItem;
import java.util.List;

public abstract interface IAttributeItemService extends IBaseService<AttributeItem, Long>
{
    public abstract DataGrid<AttributeItem> queryByAttributeItem(@ProjectCodeFlag String paramString1, PageRequest paramPageRequest, Attribute paramAttribute, String paramString2);

    public abstract List<AttributeItem> getItemSelect(@ProjectCodeFlag String paramString1, Sort paramSort, String paramString2);

    public abstract List<AttributeItem> queryByAttributeNo(@ProjectCodeFlag String paramString, Attribute paramAttribute);

    public abstract AttributeItem queryByAttrAndItemNo(@ProjectCodeFlag String paramString1, String paramString2, String paramString3);

    public abstract List<AttributeItem> listByAttributeNo(@ProjectCodeFlag String paramString1, String paramString2);

    public abstract DataGrid<AttributeItem> queryByAttributeItem(PageRequest paramPageRequest, Attribute paramAttribute, String paramString);

    public abstract List<AttributeItem> getItemSelect(Sort paramSort, String paramString);

    public abstract List<AttributeItem> queryByAttributeNo(Attribute paramAttribute);

    public abstract AttributeItem queryByAttrAndItemNo(String paramString1, String paramString2);

    public abstract List<AttributeItem> listByAttributeNo(String paramString);
}