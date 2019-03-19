package com.chenjin.test;

import java.util.*;

public class TestListSpilt {
    public static void main(String[] args) {
        List<Integer> dataList = new ArrayList<Integer>();
        for (int i = 0; i < 101; i++)
            dataList.add(i);
        if (null != dataList && dataList.size() > 0) {
            int pointsDataLimit = 10;//限制条数
            Integer size = dataList.size();
            if (pointsDataLimit < size) {
                int part = size / pointsDataLimit;//分批数
                System.out.println("共有 ： " + size + "条，！" + " 分为 ：" + part + "批");
                for (int i = 0; i < part; i++) {
                    List<Integer> listPage = dataList.subList(0, pointsDataLimit);
                    System.out.println(listPage);
                    dataList.subList(0, pointsDataLimit).clear();
                }
                if (!dataList.isEmpty()) {
                    System.out.println(dataList);//表示最后剩下的数据
                }
            } else {
                System.out.println(dataList);
            }
        } else {
            System.out.println("没有数据!!!");
        }
    }
}
