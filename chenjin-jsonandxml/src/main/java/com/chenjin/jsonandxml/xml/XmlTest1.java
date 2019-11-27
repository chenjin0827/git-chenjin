package com.chenjin.jsonandxml.xml;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * dom4j适用于小文件的解析
 */
public class XmlTest1 {
    static String fileName="E:\\gitCodeProject\\git-chenjin\\chenjin-jsonandxml\\src\\main\\resources\\stu.xml";
    public static void main(String[] args) throws DocumentException {

        SAXReader saxReader = new SAXReader();
        Document read = saxReader.read(new File(fileName));
        Element rootElement = read.getRootElement();
        getNodes(rootElement);
    }

    public static void getNodes(Element rootElement){
        System.out.println("节点名称："+rootElement.getName());
        //获取节点属性  <student1 id="001">   拿到的是这个001
        List<Attribute> attributes = rootElement.attributes();
        for(Attribute a :attributes){
            System.out.println("属性："+a.getName()+"----"+a.getValue());
        }
        if(!rootElement.getTextTrim().equals("")){
            System.out.println(rootElement.getName()+"----"+rootElement.getText());
        }
        Iterator<Element> iterator = rootElement.elementIterator();
        while(iterator.hasNext()){
            Element next = iterator.next();
            getNodes(next);
        }
    }

}
