package com.chenjin.testPCQueue.common.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

public class PageRequest
        implements PageParam, Serializable
{
    private static final long serialVersionUID = 8280485938848398236L;
    private static final int DEFAULT_PAGE_NUMBER = 1;
    private static final int DEFAULT_PAGE_SIZE = 10;
    private int pageNumber = 1;
    private int pageSize = 10;

    private int page = 1;
    private int rows = 10;

    private Map<String, Object> query = new HashMap();

    private String filterRules = "[]";

    private String filter = "{}";
    private Map<String, String> orderBy;
    private Sort mySort;
    private String sort;
    private String order;

    public PageRequest()
    {
    }

    public PageRequest(Integer pageNumber, Integer pageSize)
    {
        this(pageNumber, pageSize, null);
    }

    public PageRequest(Integer pageNumber, Integer pageSize, Sort.Direction direction, String[] properties)
    {
        this(pageNumber, pageSize, new Sort(direction, properties));
    }

    public PageRequest(Integer pageNumber, Integer pageSize, Sort sort)
    {
        if ((pageNumber == null) || (0 > pageNumber.intValue())) {
            pageNumber = Integer.valueOf(1);
        }
        if ((pageSize == null) || (0 >= pageSize.intValue())) {
            pageSize = Integer.valueOf(10);
        }

        this.pageNumber = pageNumber.intValue();
        this.pageSize = pageSize.intValue();
        this.mySort = sort;
    }

    public int getPageSize()
    {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber()
    {
        return this.pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
        this.pageNumber = page;
    }

    public int getRows() {
        return this.rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
        this.pageSize = rows;
    }

    public String getOrder()
    {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getSort() {
        return this.sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Sort getMySort() {
        return this.mySort;
    }

    public void setMySort(Sort mySort) {
        this.mySort = mySort;
    }

    public int getOffset()
    {
        return (this.pageNumber - 1) * this.pageSize;
    }

    public void setSort(Sort sort)
    {
        this.mySort = sort;
    }

    public Map<String, Object> getQuery()
    {
        return this.query;
    }

    public void setQuery(Map<String, Object> query) {
        this.query = query;
    }

    public Map<String, String> getOrderBy()
    {
        return this.orderBy;
    }

    public void setOrderBy(Map<String, String> orderBy) {
        this.orderBy = orderBy;
    }

    public String getFilter()
    {
        return this.filter;
    }

    public void setFilter(String filter) {
        addInQuery2(filter);
        this.filter = filter;
    }

    private void addInQuery2(String filter) {
        JSONObject jsonObj = JSON.parseObject(filter);
        for (Map.Entry entry : jsonObj.entrySet()) {
            if (((String)entry.getKey()).indexOf("_") > 0) {
                this.query.put("t#" + (String)entry.getKey(), entry.getValue());
            } else {
                String key = "t#" + (String)entry.getKey() + "_S_LK";
                this.query.put(key, entry.getValue());
            }

        }

        System.out.println("this.queryfilter=" + this.query);
    }

    public String getFilterRules()
    {
        return this.filterRules;
    }

    public void setFilterRules(String filterRules) {
        addInQuery(filterRules);
        this.filterRules = filterRules;
    }

    private void addInQuery(String filterRules) {
        List<JSONObject> list = JSON.parseArray(filterRules, JSONObject.class);
        for (JSONObject jsonObject : list) {
            String field = jsonObject.getString("field");
            String fieldType = jsonObject.getString("fieldType");
            fieldType = fieldType == null ? "S" : fieldType;
            String op = jsonObject.getString("op");
            op = op == null ? "LK" : op;
            String value = jsonObject.getString("value");
            if ((StringUtils.isEmpty(field)) || (StringUtils.isEmpty(value))) {
                continue;
            }
            String tablename = "t";

            if (fieldType.indexOf("#") > 0) {
                String[] arr = fieldType.split("#");
                tablename = arr[0];
                fieldType = arr[1];
            }

            String key = tablename + "#" + field + "_" + fieldType + "_" + op;
            String val = value;
            this.query.put(key, val);
        }
        System.out.println("this.query=" + this.query);
    }

    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof PageRequest)) {
            return false;
        }

        PageRequest that = (PageRequest)obj;

        boolean page = this.page == that.page;
        boolean rows = this.rows == that.rows;
        boolean pageEqual = this.pageNumber == that.pageNumber;
        boolean sizeEqual = this.pageSize == that.pageSize;

        boolean queryEqual = this.query == null ? false : that.query == null ? true : this.query.equals(that.query);
        boolean filterRulesEqual = this.filterRules == null ? false : that.filterRules == null ? true : this.filterRules.equals(that.filterRules);
        boolean filterEqual = this.filter == null ? false : that.filter == null ? true : this.filter.equals(that.filter);
        boolean orderByEqual = this.orderBy == null ? false : that.orderBy == null ? true : this.orderBy.equals(that.orderBy);
        boolean sortEqual = this.mySort == null ? false : that.mySort == null ? true : this.mySort.equals(that.mySort);

        return (page) && (rows) && (pageEqual) && (sizeEqual) && (sortEqual) && (queryEqual) && (filterRulesEqual) && (filterEqual) && (orderByEqual);
    }

    public int hashCode()
    {
        int result = 17;

        result = 31 * result + this.pageNumber;
        result = 31 * result + this.pageSize;
        result = 31 * result + (null == this.mySort ? 0 : this.mySort.hashCode());

        return result;
    }
}