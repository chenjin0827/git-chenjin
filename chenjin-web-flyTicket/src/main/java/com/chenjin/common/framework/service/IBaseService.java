package com.chenjin.common.framework.service;

import com.chenjin.common.entity.DataGrid;
import com.chenjin.common.entity.PageParam;
import com.chenjin.common.entity.Sort;
import com.chenjin.common.entity.UserHelper;
import com.chenjin.common.framework.annotation.ProjectCodeFlag;
import java.io.Serializable;
import java.util.List;

public abstract interface IBaseService<T, ID extends Serializable>
{
    public abstract T save(T paramT);

    public abstract T save(@ProjectCodeFlag String paramString, T paramT);

    public abstract T save(@ProjectCodeFlag UserHelper paramUserHelper, T paramT);

    public abstract void delete(ID paramID);

    public abstract void delete(@ProjectCodeFlag String paramString, ID paramID);

    public abstract void delete(@ProjectCodeFlag UserHelper paramUserHelper, ID paramID);

    public abstract void delete(T paramT);

    public abstract void delete(@ProjectCodeFlag String paramString, T paramT);

    public abstract void delete(@ProjectCodeFlag UserHelper paramUserHelper, T paramT);

    public abstract void delete(ID[] paramArrayOfID);

    public abstract T update(T paramT);

    public abstract T update(@ProjectCodeFlag String paramString, T paramT);

    public abstract T update(@ProjectCodeFlag UserHelper paramUserHelper, T paramT);

    public abstract T update(T paramT, String[] paramArrayOfString);

    public abstract T update(@ProjectCodeFlag String paramString, T paramT, String[] paramArrayOfString);

    public abstract T update(@ProjectCodeFlag UserHelper paramUserHelper, T paramT, String[] paramArrayOfString);

    public abstract void saveOrUpdate(T paramT);

    public abstract void saveOrUpdate(@ProjectCodeFlag String paramString, T paramT);

    public abstract void saveOrUpdate(@ProjectCodeFlag UserHelper paramUserHelper, T paramT);

    public abstract T updateWithExclude(T paramT, String[] paramArrayOfString);

    public abstract T updateWithExclude(@ProjectCodeFlag String paramString, T paramT, String[] paramArrayOfString);

    public abstract T updateWithExclude(@ProjectCodeFlag UserHelper paramUserHelper, T paramT, String[] paramArrayOfString);

    public abstract T updateWithInclude(T paramT, String[] paramArrayOfString);

    public abstract T updateWithInclude(@ProjectCodeFlag UserHelper paramUserHelper, T paramT, String[] paramArrayOfString);

    public abstract T updateWithInclude(@ProjectCodeFlag String paramString, T paramT, String[] paramArrayOfString);

    public abstract T merge(@ProjectCodeFlag String paramString, T paramT);

    public abstract T merge(@ProjectCodeFlag UserHelper paramUserHelper, T paramT);

    public abstract T merge(T paramT);

    public abstract T mergeWithExclude(T paramT, String[] paramArrayOfString);

    public abstract T mergeWithExclude(@ProjectCodeFlag String paramString, T paramT, String[] paramArrayOfString);

    public abstract T mergeWithExclude(@ProjectCodeFlag UserHelper paramUserHelper, T paramT, String[] paramArrayOfString);

    public abstract T mergeWithInclude(T paramT, String[] paramArrayOfString);

    public abstract T mergeWithInclude(@ProjectCodeFlag String paramString, T paramT, String[] paramArrayOfString);

    public abstract T mergeWithInclude(@ProjectCodeFlag UserHelper paramUserHelper, T paramT, String[] paramArrayOfString);

    public abstract T getById(ID paramID);

    public abstract T getById(@ProjectCodeFlag String paramString, ID paramID);

    public abstract T getById(@ProjectCodeFlag UserHelper paramUserHelper, ID paramID);

    public abstract Long count();

    public abstract DataGrid<T> query(PageParam paramPageParam);

    public abstract DataGrid<T> query(@ProjectCodeFlag String paramString, PageParam paramPageParam);

    public abstract DataGrid<T> query(@ProjectCodeFlag UserHelper paramUserHelper, PageParam paramPageParam);

    public abstract List<T> list(PageParam paramPageParam);

    public abstract List<T> list(@ProjectCodeFlag String paramString, PageParam paramPageParam);

    public abstract List<T> list(@ProjectCodeFlag UserHelper paramUserHelper, PageParam paramPageParam);

    public abstract List<T> findList(ID[] paramArrayOfID);

    public abstract List<T> limit(int paramInt, Sort paramSort);

    public abstract List<T> limit(@ProjectCodeFlag String paramString, int paramInt, Sort paramSort);

    public abstract List<T> limit(@ProjectCodeFlag UserHelper paramUserHelper, int paramInt, Sort paramSort);

    public abstract T getByKey(PageParam paramPageParam);

    public abstract T getByKey(@ProjectCodeFlag String paramString, PageParam paramPageParam);

    public abstract T getByKey(@ProjectCodeFlag UserHelper paramUserHelper, PageParam paramPageParam);

    public abstract int saveBatch(@ProjectCodeFlag String paramString, List<T> paramList);

    public abstract int saveBatch(@ProjectCodeFlag UserHelper paramUserHelper, List<T> paramList);

    public abstract int updateBatch(@ProjectCodeFlag String paramString, List<T> paramList);

    public abstract int updateBatch(@ProjectCodeFlag UserHelper paramUserHelper, List<T> paramList);

    public abstract int updateBatchWithInclude(@ProjectCodeFlag String paramString, List<T> paramList, String[] paramArrayOfString);

    public abstract int updateBatchWithInclude(@ProjectCodeFlag UserHelper paramUserHelper, List<T> paramList, String[] paramArrayOfString);
}