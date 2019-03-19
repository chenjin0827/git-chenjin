package com.chenjin.testPCQueue.common.framework.aspect;

import com.chenjin.testPCQueue.common.framework.datasource.DynamicDataSource;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DSParams
{
    private DynamicDataSource dataSource;
    private Map<String, String> map = new HashMap();
    private Map<String, Element> jdbc = new HashMap();
    private static Logger logger = LoggerFactory.getLogger(DSParams.class);

    public Map<String, String> getMap()
    {
        return this.map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public Map<String, Element> getJdbc() {
        return this.jdbc;
    }

    public void setJdbc(Map<String, Element> jdbc) {
        this.jdbc = jdbc;
    }

    public DynamicDataSource getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(DynamicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String getDSName(String projectCode) {
        return (String)this.map.get(projectCode);
    }

    public void init()
    {
        try
        {
            InputStream in = DSParams.class.getResourceAsStream("/db.xml");
            Document document = new SAXReader().read(in);
            Element element;
            List elements1 = document.selectNodes("/db/projectCode");
            for (Iterator localIterator = elements1.iterator(); localIterator.hasNext(); ) { element = (Element)localIterator.next();
                String name = element.attributeValue("name");
                String value = element.attributeValue("value");
                this.map.put(name, value);
                logger.info("name=" + name);
                logger.info("value=" + value);
            }

            List<Element> elements2 = document.selectNodes("/db/dataSource");
            for (Element element1 : elements2) {
                String name = element1.attributeValue("name");
                this.jdbc.put(name, element1);
            }

            this.dataSource.updateTargetDataSource(this);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
    }
}