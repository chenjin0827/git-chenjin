package com.chenjin.common.entity;

import java.util.Map;

public abstract interface PageParam
{
    public abstract Map<String, Object> getQuery();

    public abstract String getFilterRules();

    public abstract String getFilter();

    public abstract void setSort(Sort paramSort);

    public abstract Map<String, String> getOrderBy();

    public abstract int getPageNumber();

    public abstract int getPageSize();

    public abstract int getOffset();

    public abstract Sort getMySort();

    public abstract String getSort();

    public abstract String getOrder();
}