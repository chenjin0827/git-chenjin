package com.chenjin.testPCQueue.common.framework.dao;

import com.chenjin.testPCQueue.common.entity.DataGrid;
import com.chenjin.testPCQueue.common.entity.PageParam;
import com.chenjin.testPCQueue.common.entity.Sort;
import java.io.Serializable;
import java.util.List;

import org.hibernate.LockOptions;

public abstract interface IBaseDao<T, ID extends Serializable>
{
    public abstract T getById(ID paramID);

    public abstract T get(ID paramID, LockOptions paramLockOptions);

    public abstract void lock(T paramT, LockOptions paramLockOptions);

    public abstract T save(T paramT);

    public abstract void delete(T paramT);

    public abstract T update(T paramT);

    public abstract void saveOrUpdate(T paramT);

    public abstract T persist(T paramT);

    public abstract T merge(T paramT);

    public abstract Long count(String paramString, Object[] paramArrayOfObject);

    public abstract List<T> getAll(PageParam paramPageParam);

    public abstract List<T> limit(int paramInt, Sort paramSort);

    public abstract DataGrid<T> query(PageParam paramPageParam);

    public abstract T getByKey(PageParam paramPageParam);

    public abstract boolean isManaged(T paramT);

    public abstract void clear();

    public abstract void flush();

    public abstract void evict(T paramT);

    public abstract void refresh(T paramT);

    public abstract void refresh(T paramT, LockOptions paramLockOptions);

    public abstract ID getIdentifier(T paramT);

    public abstract int saveBatch(List<T> paramList);

    public abstract int updateBatch(List<T> paramList);

    public abstract int updateBatchWithInclude(List<T> paramList, String[] paramArrayOfString);

    public abstract int deleteBatch(List<T> paramList);

    public abstract T saveJDBC(T paramT);

    public abstract int updateJDBC(T paramT);

    public abstract int deleteJDBC(T paramT);
}