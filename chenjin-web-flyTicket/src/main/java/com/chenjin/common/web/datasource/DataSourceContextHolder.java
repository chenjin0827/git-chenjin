package com.chenjin.common.web.datasource;

import javax.servlet.http.HttpSession;

public class DataSourceContextHolder
{
    public static void setDataSource(String dataSource)
    {
        ApplicationContextHolder.getSession().setAttribute("_datasource", dataSource);
    }

    public static String getDataSource() {
        try {
            return (String)ApplicationContextHolder.getSession().getAttribute("_datasource");
        } catch (Exception e) {
            e.printStackTrace();
        }return null;
    }

    public static void clear()
    {
        ApplicationContextHolder.getSession().removeAttribute("_datasource");
    }

    public static void setDefaultDataSource()
    {
        ApplicationContextHolder.getSession().removeAttribute("_datasource");
    }
}