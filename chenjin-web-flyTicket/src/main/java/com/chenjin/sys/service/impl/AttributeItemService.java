package com.chenjin.sys.service.impl;

import com.chenjin.common.entity.DataGrid;
import com.chenjin.common.entity.PageRequest;
import com.chenjin.common.entity.Sort;
import com.chenjin.common.framework.service.BaseService;
import com.chenjin.sys.dao.IAttributeItemDao;
import com.chenjin.sys.entity.Attribute;
import com.chenjin.sys.entity.AttributeItem;
import com.chenjin.sys.service.IAttributeItemService;
import com.chenjin.sys.service.IAttributeService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class AttributeItemService extends BaseService<AttributeItem, Long>
        implements IAttributeItemService
{
    private IAttributeItemDao attributeItemDao;

    @Resource(name="attributeService")
    private IAttributeService attributeService;

    public IAttributeItemDao getAttributeItemDao()
    {
        return this.attributeItemDao;
    }
    @Resource(name="attributeItemDao")
    public void setAttributeItem(IAttributeItemDao attributeItemDao) {
        this.attributeItemDao = attributeItemDao;
        super.setBaseDao(attributeItemDao);
    }
    @Transactional
    @CacheEvict(value={"attributeItem"}, allEntries=true)
    public AttributeItem save(String projectCode, AttributeItem entity) { return (AttributeItem)super.save(projectCode, entity); }
    @Transactional
    @CacheEvict(value={"attributeItem"}, allEntries=true)
    public AttributeItem updateWithInclude(String projectCode, AttributeItem entity, String[] args) {
        return (AttributeItem)super.updateWithInclude(projectCode, entity, args);
    }
    @Transactional
    @CacheEvict(value={"attributeItem"}, allEntries=true)
    public AttributeItem update(String projectCode, AttributeItem entity) { return (AttributeItem)super.update(projectCode, entity); }
    @Transactional
    @CacheEvict(value={"attributeItem"}, allEntries=true)
    public void delete(String projectCode, Long id) {
        super.delete(projectCode, id);
    }

    @Cacheable({"attributeItem"})
    public DataGrid<AttributeItem> queryByAttributeItem(String projectCode, PageRequest pageablem, Attribute attribute, String searchkey)
    {
        return this.attributeItemDao.queryByAttributeItem(pageablem, attribute, searchkey);
    }

    public List<AttributeItem> getItemSelect(String projectCode, Sort sort, String attributeNo)
    {
        return this.attributeItemDao.getItemSelect(sort, attributeNo);
    }

    public List<AttributeItem> queryByAttributeNo(String projectCode, Attribute attribute)
    {
        return this.attributeItemDao.queryByAttributeNo(attribute);
    }

    public AttributeItem queryByAttrAndItemNo(String projectCode, String attributeNo, String filed1)
    {
        return this.attributeItemDao.queryByAttrAndItemNo(attributeNo, filed1);
    }

    public List<AttributeItem> listByAttributeNo(String projectCode, String attributeNo)
    {
        return this.attributeItemDao.listByAttributeNo(attributeNo);
    }

    public DataGrid<AttributeItem> queryByAttributeItem(PageRequest pageablem, Attribute attribute, String searchkey)
    {
        return queryByAttributeItem("", pageablem, attribute, searchkey);
    }

    public List<AttributeItem> getItemSelect(Sort sort, String attributeNo)
    {
        return getItemSelect("", sort, attributeNo);
    }

    public List<AttributeItem> queryByAttributeNo(Attribute attribute)
    {
        return queryByAttributeNo("", attribute);
    }

    public AttributeItem queryByAttrAndItemNo(String attributeNo, String string)
    {
        return queryByAttrAndItemNo("", attributeNo, string);
    }

    public List<AttributeItem> listByAttributeNo(String attributeNo)
    {
        return listByAttributeNo("", attributeNo);
    }
}