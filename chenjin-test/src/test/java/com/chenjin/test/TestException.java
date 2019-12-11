package com.chenjin.test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class TestException {


    //返回对应编码
    private String getExcelFileName(String userAgent, String fileName) throws UnsupportedEncodingException {
        if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } else {
            // 非IE浏览器的处理：
            byte[] bytes = fileName.getBytes("UTF-8");
            fileName = new String(bytes, "UTF-8");
        }
        return fileName;
    }

    public static void main(String[] args) {
        TestException testException = new TestException();
        String userAgent = "MSI11E";
        String fileName = "我是苹果";
        try {
            String excelFileName = testException.getExcelFileName(userAgent, fileName);
            System.out.println("excelFileName====" + excelFileName);
            System.out.println(111);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }

}
