package com.chenjin.sys.dao.impl;

import com.chenjin.common.entity.DataGrid;
import com.chenjin.common.entity.PageRequest;
import com.chenjin.common.entity.Sort;
import com.chenjin.common.entity.Sort.Direction;
import com.chenjin.common.framework.dao.BaseDao;
import com.chenjin.sys.dao.IAttributeItemDao;
import com.chenjin.sys.entity.Attribute;
import com.chenjin.sys.entity.AttributeItem;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository("attributeItemDao")
public class AttributeItemDao extends BaseDao<AttributeItem, Long>
        implements IAttributeItemDao
{
    public DataGrid<AttributeItem> queryByAttributeItem(PageRequest pageablem, Attribute attribute, String searchkey)
    {
        String hql = "";
        if (attribute.getId() != null) {
            hql = " from AttributeItem t where t.attribute =?  ";
        }
        if (!StringUtils.isEmpty(searchkey)) {
            hql = hql + " and t.field1= '" + searchkey + "'";
        }
        return super.query(hql, pageablem, new Object[] {
                attribute });
    }

    public List<AttributeItem> getItemSelect(Sort sort, String attributeNo)
    {
        String hql = " from AttributeItem t  left join fetch t.attribute a where a.attributeNo = ?";
        return super.listByHql(hql, sort, new Object[] { attributeNo });
    }

    public List<AttributeItem> queryByAttributeNo(Attribute attribute)
    {
        String hql = " from AttributeItem t where t.attribute =?  ";
        Sort sort = new Sort(Sort.Direction.ASC, new String[] { "id" });
        return super.listByHql(hql, sort, new Object[] { attribute });
    }

    public AttributeItem queryByAttrAndItemNo(String attributeNo, String filed1) {
        String hql = " from AttributeItem t  left join fetch t.attribute a where a.attributeNo = ? and t.field1 = ? ";
        return (AttributeItem)super.getByHql(hql, new Object[] { attributeNo, filed1 });
    }

    public AttributeItem queryByAttrAndField2(String attributeNo, String filed2)
    {
        String hql = " from AttributeItem t  left join fetch t.attribute a where a.attributeNo = ? and t.field2 = ? ";
        return (AttributeItem)super.getByHql(hql, new Object[] { attributeNo, filed2 });
    }

    public String getMaxCode(String attributeNo)
    {
        String code = (String)getBySql("select max(a.field1) from t_set_attribute_item a,t_set_attribute b where a.attributeid=b.id and a.field1 like '__' and b.attributeno=?",
                null, new Object[] { attributeNo });
        if (code == null) {
            code = "01";
        }
        else if (Integer.valueOf(code).intValue() + 1 < 10)
            code = "0" + Integer.toString(Integer.valueOf(code).intValue() + 1);
        else {
            code = Integer.toString(Integer.valueOf(code).intValue() + 1);
        }

        return code;
    }

    public List<AttributeItem> listByAttributeNo(String attributeNo) {
        String hql = " from AttributeItem t  left join fetch t.attribute a where a.attributeNo = ?";
        return super.listByHql(hql, null, new Object[] { attributeNo });
    }
}