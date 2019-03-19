package com.chenjin.testPCQueue.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class DubboUtil
{
    String comPath;
    String comPathDot;
    String perPath = System.getProperty("user.dir") + "/src/main/java/";
    String jspPath = System.getProperty("user.dir") + "/src/main/webapp/WEB-INF/jsp/";
    String dubboPath = System.getProperty("user.dir") + "/src/main/resources/";
    String action = "00";
    String entityPath = "com.chenjin.medicshop.service.base";
    String suf = "";

    public DubboUtil(String entityPath, String action, String suf) {
        if (entityPath != null)
            this.entityPath = entityPath;
        if (action != null)
            this.action = action;
        if (suf != null)
            this.suf = suf;
    }

    public DubboUtil(String entityPath, String action) {
        if (entityPath != null)
            this.entityPath = entityPath;
        if (action != null)
            this.action = action;
    }

    public DubboUtil(String entityPath) {
        if (entityPath != null)
            this.entityPath = entityPath;
    }

    public boolean scanEntity()
            throws FileNotFoundException, IOException
    {
        try
        {
            String filepath = this.entityPath;

            filepath = filepath.replaceAll("[.]", "/");
            this.comPath = (filepath.substring(0, filepath.lastIndexOf("/")) + "/");
            this.comPathDot = this.comPath.replaceAll("[/]", ".");

            filepath = this.perPath + filepath;
            File file = new File(filepath + "/" + this.suf);
            if (!file.isDirectory()) {
                System.out.println("不是文件夹");
            } else if (file.isDirectory()) {
                System.out.println("文件夹");
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File readfile = new File(filepath + "/" + filelist[i]);
                    if (!readfile.isDirectory()) {
                        String fileName = readfile.getName();
                        fileName = fileName.substring(0, fileName.indexOf("."));
                        System.out.println("fileName=" + fileName);

                        if (this.action.substring(0, 1).equals("1")) {
                            generateDubboProvider(fileName);
                        }
                        if (this.action.substring(1, 2).equals("1"))
                            generateDubboConsumer(fileName);
                    }
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("readfile()   Exception:" + e.getMessage());
        }
        return true;
    }

    private void generateDubboConsumer(String fileName)
            throws IOException
    {
        File dubboFile = new File(this.dubboPath + "/dubbo-consumer.xml");
        if (dubboFile.exists()) {
            FileOutputStream out = new FileOutputStream(dubboFile, true);

            StringBuffer sb = new StringBuffer();
            String id = fileName;
            id = id.substring(0, 1).toLowerCase() + id.substring(1);
            sb.append("\t<dubbo:reference id=\"" + id + "\" interface=\"" + this.entityPath.replace(".impl", "") + "." + this.suf + ".I" + fileName + "\" />\n");

            out.write(sb.toString().getBytes("utf-8"));
            out.close();
        }
    }

    private void generateDubboProvider(String fileName)
            throws IOException
    {
        File dubboFile = new File(this.dubboPath + "/dubbo-provider.xml");
        if (dubboFile.exists()) {
            FileOutputStream out = new FileOutputStream(dubboFile, true);

            StringBuffer sb = new StringBuffer();
            String id = fileName;
            id = id.substring(0, 1).toLowerCase() + id.substring(1);
            sb.append("\t<dubbo:service ref=\"" + id + "\" interface=\"" + this.entityPath.replace(".impl", "") + "." + this.suf + ".I" + fileName + "\" />\n");
            out.write(sb.toString().getBytes("utf-8"));
            out.close();
        }
    }
}