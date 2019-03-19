package com.chenjin.testPCQueue.sys.service.impl;

import com.chenjin.testPCQueue.common.entity.DataGrid;
import com.chenjin.testPCQueue.common.entity.PageParam;
import com.chenjin.testPCQueue.common.entity.PageRequest;
import com.chenjin.testPCQueue.common.framework.service.BaseService;
import com.chenjin.testPCQueue.sys.dao.IAttributeDao;
import com.chenjin.testPCQueue.sys.entity.Attribute;
import com.chenjin.testPCQueue.sys.service.IAttributeService;
import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class AttributeService extends BaseService<Attribute, Long>
        implements IAttributeService
{
    private IAttributeDao attributeDao;

    public IAttributeDao getAttributeDao()
    {
        return this.attributeDao;
    }
    @Resource(name="attributeDao")
    public void setAttribute(IAttributeDao attributeDao) {
        this.attributeDao = attributeDao;
        super.setBaseDao(attributeDao);
    }
    @Transactional
    @CacheEvict(value={"attribute"}, allEntries=true)
    public Attribute save(String projectCode, Attribute entity) { return (Attribute)super.save(projectCode, entity); }
    @Transactional
    @CacheEvict(value={"attribute"}, allEntries=true)
    public Attribute updateWithInclude(String projectCode, Attribute entity, String[] args) {
        return (Attribute)super.updateWithInclude(projectCode, entity, args);
    }
    @Transactional
    @CacheEvict(value={"attribute"}, allEntries=true)
    public Attribute update(String projectCode, Attribute entity) { return (Attribute)super.update(projectCode, entity); }
    @Transactional
    @CacheEvict(value={"attribute"}, allEntries=true)
    public void delete(String projectCode, Long id) {
        super.delete(projectCode, id);
    }

    @Cacheable({"attribute"})
    public Attribute queryByAttributeNo(String projectCode, PageRequest pageable, String attributeNo) {
        return this.attributeDao.queryByAttributeNo(pageable, attributeNo);
    }

    @Cacheable({"attribute"})
    public DataGrid<Attribute> query(String projectCode, PageParam pageable) {
        return super.query(projectCode, pageable);
    }
}