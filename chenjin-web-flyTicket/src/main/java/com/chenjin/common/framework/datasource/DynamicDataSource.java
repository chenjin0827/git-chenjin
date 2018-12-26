package com.chenjin.common.framework.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.chenjin.common.framework.aspect.DSParams;
import com.chenjin.common.util.ThreadLocalUtil;
import java.sql.SQLException;
import java.util.Map;
import javax.sql.DataSource;
import org.dom4j.Element;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.util.StringUtils;

public class DynamicDataSource extends AbstractRoutingDataSource
{
    private Map<Object, Object> _targetDataSources;

    protected Object determineCurrentLookupKey()
    {
        return ThreadLocalUtil.getDataSource();
    }

    public void setTargetDataSources(Map<Object, Object> targetDataSources)
    {
        this._targetDataSources = targetDataSources;
        super.setTargetDataSources(this._targetDataSources);
        afterPropertiesSet();
    }

    public void updateTargetDataSource(DSParams dSParams) {
        for (String key : dSParams.getMap().keySet()) {
            String dataSourceName = (String)dSParams.getMap().get(key);
            Object obj = this._targetDataSources.get(dataSourceName);
            if (obj == null) {
                DataSource dataSource = getDataSource((Element)dSParams.getJdbc().get(dataSourceName));
                this._targetDataSources.put(dataSourceName, dataSource);
            }
        }
        setTargetDataSources(this._targetDataSources);
    }

    private DataSource getDataSource(Element element) {
        DataSource dataSource = null;

        String type = element.attributeValue("type");

        if ((!StringUtils.isEmpty(type)) && (type.equals("JDBC")))
        {
            String jdbc_driver = element.attributeValue("jdbc_driver");
            String jdbc_url = element.attributeValue("jdbc_url");
            String jdbc_username = element.attributeValue("jdbc_username");
            String jdbc_password = element.attributeValue("jdbc_password");
            String maxActive = element.attributeValue("maxActive");
            String maxWait = element.attributeValue("maxWait");
            String validationQuery = element.attributeValue("validationQuery");
            try {
                dataSource = createDataSource(jdbc_driver, jdbc_url, jdbc_username, jdbc_password, Integer.parseInt(maxActive), Integer.parseInt(maxWait), validationQuery);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if ((!StringUtils.isEmpty(type)) && (type.equals("JNDI")))
        {
            String jndi_name = element.attributeValue("jndi_name");

            JndiDataSourceLookup jndiLookUp = new JndiDataSourceLookup();
            dataSource = jndiLookUp.getDataSource(jndi_name);
        }

        return dataSource;
    }

    private DataSource createDataSource(String driverClassName, String url, String username, String password, int maxActive, int maxWait, String validationQuery) throws SQLException
    {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        dataSource.setInitialSize(0);
        dataSource.setMaxActive(maxActive);
        dataSource.setMinIdle(0);
        dataSource.setMaxWait(maxWait);
        dataSource.setValidationQuery(validationQuery);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setTestWhileIdle(true);
        dataSource.setTimeBetweenEvictionRunsMillis(60000L);
        dataSource.setMinEvictableIdleTimeMillis(25200000L);
        dataSource.setRemoveAbandoned(true);
        dataSource.setRemoveAbandonedTimeout(1800);
        dataSource.setLogAbandoned(true);
        dataSource.setFilters("stat");
        return dataSource;
    }
}