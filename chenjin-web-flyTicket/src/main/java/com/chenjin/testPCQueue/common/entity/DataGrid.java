package com.chenjin.testPCQueue.common.entity;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.util.Assert;

public class DataGrid<T>
        implements Serializable
{
    private static final long serialVersionUID = 4088303287906663058L;
    private long total;
    private int pageNum = 1;

    private int pageSize = 10;

    private List<T> rows = new ArrayList();

    private List<Map<String, Object>> footer = new ArrayList();

    public DataGrid()
    {
    }

    public DataGrid(List<T> datas, PageParam pageable, long totalElements)
    {
        Assert.notNull(datas, "datas must not be null!");
        this.total = totalElements;
        this.rows.addAll(datas);
        if (pageable != null) {
            this.pageNum = pageable.getPageNumber();
            this.pageSize = pageable.getPageNumber();
        }
    }

    public long getTotal()
    {
        return this.total;
    }
    public void setTotal(long totalElements) {
        this.total = totalElements;
    }

    public int getPageNum() {
        return this.pageNum;
    }
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return this.pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public List<T> getRows() {
        return Collections.unmodifiableList(this.rows);
    }
    public void setRows(List<T> datas) {
        this.rows = datas;
    }

    public List<Map<String, Object>> getFooter() {
        return this.footer;
    }
    public void setFooter(List<Map<String, Object>> footer) {
        this.footer = footer;
    }

    public boolean hasContent()
    {
        return !this.rows.isEmpty();
    }

    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof DataGrid)) {
            return false;
        }

        DataGrid that = (DataGrid)obj;

        return (this.total == that.total) && (super.equals(obj));
    }

    public void addFooter(String title, String[] keys)
    {
        try
        {
            List l = getRows();
            if (l.size() == 0) {
                return;
            }
            Map footerMap = new HashMap();
            footerMap.put(title, "合计：");

            for (String key : keys) {
                String key1 = upFirstLetter(key);
                BigDecimal b = new BigDecimal("0");

                for (Iterator localIterator = l.iterator(); localIterator.hasNext(); ) { Object t = localIterator.next();

                    if ((t instanceof HashMap)) {
                        Map m = (Map)t;
                        Object val = m.get(key);
                        if (val != null)
                            b = b.add(new BigDecimal(val.toString()));
                    }
                    else {
                        Method m = t.getClass().getMethod("get" + key1, new Class[0]);
                        Object val = m.invoke(t, new Object[0]);
                        if (val != null) {
                            b = b.add(new BigDecimal(val.toString()));
                        }
                    }

                }

                footerMap.put(key, b);
            }

            this.footer.add(footerMap);
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String upFirstLetter(String key)
    {
        if ((key == null) || (key.length() == 0)) {
            return "";
        }
        return key.substring(0, 1).toUpperCase() + key.substring(1);
    }
}