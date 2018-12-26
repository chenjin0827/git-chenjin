package com.chenjin.common.framework.service;

import com.chenjin.common.entity.DataGrid;
import com.chenjin.common.entity.PageParam;
import com.chenjin.common.entity.Sort;
import com.chenjin.common.entity.UserHelper;
import com.chenjin.common.framework.dao.IBaseDao;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Transactional(readOnly=true)
public class BaseService<T, ID extends Serializable>
        implements IBaseService<T, ID>
{
    private static final String[] UPDATE_IGNORE_PROPERTIES = { "id", "createDate", "modifyDate", "createUser", "modifyUser" };
    private IBaseDao<T, ID> baseDao;

    public void setBaseDao(IBaseDao<T, ID> baseDao)
    {
        this.baseDao = baseDao;
    }

    @Transactional
    public T save(String projectCode, T entity) {
        return this.baseDao.save(entity);
    }

    @Transactional
    public void delete(String projectCode, ID id) {
        this.baseDao.delete(this.baseDao.getById(id));
    }

    @Transactional
    public void delete(String projectCode, T entity) {
        this.baseDao.delete(entity);
    }

    @Transactional
    public T update(String projectCode, T entity) {
        return this.baseDao.update(entity);
    }

    @Transactional
    public void saveOrUpdate(String projectCode, T entity) {
        this.baseDao.saveOrUpdate(entity);
    }

    @Transactional
    public T update(String projectCode, T entity, String[] ignoreProperties) {
        Assert.notNull(entity);
        if (this.baseDao.isManaged(entity)) {
            throw new IllegalArgumentException("Entity must not be managed");
        }
        T persistant = this.baseDao.getById(this.baseDao.getIdentifier(entity));
        if (persistant != null) {
            copyProperties(entity, persistant, (String[])(String[])ArrayUtils.addAll(ignoreProperties, UPDATE_IGNORE_PROPERTIES));
            return update(projectCode, persistant);
        }
        return update(projectCode, entity);
    }

    @Transactional
    public T updateWithExclude(String projectCode, T entity, String[] args)
    {
        T old = this.baseDao.getById(this.baseDao.getIdentifier(entity));
        if (old != null)
        {
            try {
                PropertyUtils.setProperty(entity, "createUser", PropertyUtils.getProperty(old, "createUser"));
                PropertyUtils.setProperty(entity, "createDate", PropertyUtils.getProperty(old, "createDate"));

                for (String arg : args) {
                    PropertyUtils.setProperty(entity, arg, PropertyUtils.getProperty(old, arg));
                }

                PropertyUtils.copyProperties(old, entity);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this.baseDao.update(old);
        }
        return update(projectCode, entity);
    }

    @Transactional
    public T updateWithInclude(String projectCode, T entity, String[] args)
    {
        T old = this.baseDao.getById(this.baseDao.getIdentifier(entity));
        if (old != null) {
            try {
                for (String arg : args)
                    PropertyUtils.setProperty(old, arg, PropertyUtils.getProperty(entity, arg));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return this.baseDao.update(old);
        }
        return update(projectCode, entity);
    }

    @Transactional
    public T merge(String projectCode, T entity)
    {
        return this.baseDao.merge(entity);
    }

    @Transactional
    public T mergeWithExclude(String projectCode, T entity, String[] args) {
        T old = this.baseDao.getById(this.baseDao.getIdentifier(entity));
        if (old != null)
        {
            try {
                PropertyUtils.setProperty(entity, "createUser", PropertyUtils.getProperty(old, "createUser"));
                PropertyUtils.setProperty(entity, "createDate", PropertyUtils.getProperty(old, "createDate"));

                for (String arg : args) {
                    PropertyUtils.setProperty(entity, arg, PropertyUtils.getProperty(old, arg));
                }

                PropertyUtils.copyProperties(old, entity);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this.baseDao.merge(old);
        }
        return merge(projectCode, entity);
    }

    @Transactional
    public T mergeWithInclude(String projectCode, T entity, String[] args)
    {
        T old = this.baseDao.getById(this.baseDao.getIdentifier(entity));
        if (old != null) {
            try {
                for (String arg : args)
                    PropertyUtils.setProperty(old, arg, PropertyUtils.getProperty(entity, arg));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return this.baseDao.merge(old);
        }
        return merge(projectCode, entity);
    }

    @Transactional(readOnly=true)
    public T getById(String projectCode, ID id)
    {
        return this.baseDao.getById(id);
    }

    @Transactional(readOnly=true)
    public DataGrid<T> query(String projectCode, PageParam pageable) {
        return this.baseDao.query(pageable);
    }

    @Transactional(readOnly=true)
    public List<T> list(String projectCode, PageParam pageable) {
        return this.baseDao.getAll(pageable);
    }

    @Transactional(readOnly=true)
    public List<T> limit(String projectCode, int size, Sort sort) {
        return this.baseDao.limit(size, sort);
    }

    private void copyProperties(Object source, Object target, String[] ignoreProperties) throws BeansException {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");

        PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(target.getClass());
        List ignoreList = ignoreProperties != null ? Arrays.asList(ignoreProperties) : null;
        for (PropertyDescriptor targetPd : targetPds)
            if ((targetPd.getWriteMethod() != null) && ((ignoreProperties == null) || (!ignoreList.contains(targetPd.getName())))) {
                PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
                if ((sourcePd == null) || (sourcePd.getReadMethod() == null)) continue;
                try {
                    Method readMethod = sourcePd.getReadMethod();
                    if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                        readMethod.setAccessible(true);
                    }
                    Object sourceValue = readMethod.invoke(source, new Object[0]);
                    Object targetValue = readMethod.invoke(target, new Object[0]);
                    if ((sourceValue != null) && (targetValue != null) && ((targetValue instanceof Collection))) {
                        Collection collection = (Collection)targetValue;
                        collection.clear();
                        collection.addAll((Collection)sourceValue);
                    } else {
                        Method writeMethod = targetPd.getWriteMethod();
                        if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                            writeMethod.setAccessible(true);
                        }
                        writeMethod.invoke(target, new Object[] { sourceValue });
                    }
                } catch (Throwable ex) {
                    throw new FatalBeanException("Could not copy properties from source to target", ex);
                }
            }
    }

    @Transactional
    public int saveBatch(String projectCode, List<T> list)
    {
        return this.baseDao.saveBatch(list);
    }

    @Transactional
    public int updateBatch(String projectCode, List<T> list) {
        return this.baseDao.updateBatch(list);
    }

    @Transactional
    public int updateBatchWithInclude(String projectCode, List<T> list, String[] args) {
        return this.baseDao.updateBatchWithInclude(list, args);
    }

    @Transactional
    public void delete(ID[] ids)
    {
        if (ids != null)
            for (ID id : ids)
                delete("", getById("", id));
    }

    @Transactional(readOnly=true)
    public Long count()
    {
        return this.baseDao.count(null, new Object[0]);
    }

    @Transactional(readOnly=true)
    public List<T> findList(ID[] ids)
    {
        List result = new ArrayList();
        if (ids != null) {
            for (ID id : ids) {
                Object entity = getById("", id);
                if (entity != null) {
                    result.add(entity);
                }
            }
        }
        return result;
    }

    @Transactional(readOnly=true)
    public T getByKey(String projectCode, PageParam pageable)
    {
        List list = list(projectCode, pageable);
        if ((list != null) && (list.size() > 0)) {
            return (T)list.get(0);
        }
        return null;
    }

    @Transactional
    public T save(UserHelper userHelper, T entity) {
        return save(userHelper.getProjectCode(), entity);
    }

    @Transactional
    public void delete(UserHelper userHelper, ID id) {
        delete(userHelper.getProjectCode(), id);
    }

    @Transactional
    public void delete(UserHelper userHelper, T entity) {
        delete(userHelper.getProjectCode(), entity);
    }

    @Transactional
    public T update(UserHelper userHelper, T entity) {
        return update(userHelper.getProjectCode(), entity);
    }

    @Transactional
    public T update(UserHelper userHelper, T entity, String[] ignoreProperties) {
        return update(userHelper.getProjectCode(), entity, ignoreProperties);
    }

    @Transactional
    public void saveOrUpdate(UserHelper userHelper, T entity) {
        saveOrUpdate(userHelper.getProjectCode(), entity);
    }

    @Transactional
    public T updateWithExclude(UserHelper userHelper, T entity, String[] args) {
        return updateWithExclude(userHelper.getProjectCode(), entity, args);
    }

    @Transactional
    public T updateWithInclude(UserHelper userHelper, T entity, String[] args) {
        return updateWithInclude(userHelper.getProjectCode(), entity, args);
    }

    @Transactional
    public T merge(UserHelper userHelper, T entity) {
        return merge(userHelper.getProjectCode(), entity);
    }

    @Transactional
    public T mergeWithExclude(UserHelper userHelper, T entity, String[] args) {
        return mergeWithExclude(userHelper.getProjectCode(), entity, args);
    }

    @Transactional
    public T mergeWithInclude(UserHelper userHelper, T entity, String[] args) {
        return mergeWithInclude(userHelper.getProjectCode(), entity, args);
    }

    public DataGrid<T> query(UserHelper userHelper, PageParam pageable)
    {
        return query(userHelper.getProjectCode(), pageable);
    }

    public List<T> list(UserHelper userHelper, PageParam pageable)
    {
        return list(userHelper.getProjectCode(), pageable);
    }

    public List<T> limit(UserHelper userHelper, int size, Sort sort)
    {
        return limit(userHelper.getProjectCode(), size, sort);
    }

    public T getByKey(UserHelper userHelper, PageParam pageable)
    {
        return getByKey(userHelper.getProjectCode(), pageable);
    }

    @Transactional
    public int updateBatch(UserHelper userHelper, List<T> list) {
        return updateBatch(userHelper.getProjectCode(), list);
    }

    @Transactional
    public int updateBatchWithInclude(UserHelper userHelper, List<T> list, String[] args) {
        return updateBatchWithInclude(userHelper.getProjectCode(), list, args);
    }

    public T getById(UserHelper userHelper, ID id)
    {
        return getById(userHelper.getProjectCode(), id);
    }

    @Transactional
    public int saveBatch(UserHelper userHelper, List<T> list) {
        return saveBatch(userHelper.getProjectCode(), list);
    }

    @Transactional
    public T save(T entity)
    {
        return save("", entity);
    }

    @Transactional
    public void delete(ID id) {
        delete("", id);
    }

    @Transactional
    public void delete(T entity) {
        delete("", entity);
    }
    @Transactional
    public T update(T entity) {
        return update("", entity);
    }

    @Transactional
    public T update(T entity, String[] ignoreProperties) {
        return update("", entity, ignoreProperties);
    }

    @Transactional
    public void saveOrUpdate(T entity) {
        saveOrUpdate("", entity);
    }

    @Transactional
    public T updateWithExclude(T entity, String[] args) {
        return updateWithExclude("", entity, args);
    }

    @Transactional
    public T updateWithInclude(T entity, String[] args) {
        return updateWithInclude("", entity, args);
    }
    @Transactional
    public T merge(T entity) {
        return merge("", entity);
    }
    @Transactional
    public T mergeWithExclude(T entity, String[] args) {
        return mergeWithExclude("", entity, args);
    }

    @Transactional
    public T mergeWithInclude(T entity, String[] args) {
        return mergeWithInclude("", entity, args);
    }

    @Transactional(readOnly=true)
    public T getById(ID id) {
        return getById("", id);
    }
    @Transactional(readOnly=true)
    public DataGrid<T> query(PageParam pageable) {
        return query("", pageable);
    }

    @Transactional(readOnly=true)
    public List<T> list(PageParam pageable) {
        return list("", pageable);
    }

    @Transactional(readOnly=true)
    public List<T> limit(int size, Sort sort) {
        return limit("", size, sort);
    }
    @Transactional(readOnly=true)
    public T getByKey(PageParam pageable) {
        List list = list(pageable);
        if ((list != null) && (list.size() > 0)) {
            return (T)list.get(0);
        }
        return null;
    }
}