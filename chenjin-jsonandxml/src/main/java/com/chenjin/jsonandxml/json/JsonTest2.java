package com.chenjin.jsonandxml.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 组装json
 */
public class JsonTest2 {

    public static void main(String[] args) {
        JSONObject jo = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject bean1 = new JSONObject();
        JSONObject bean2 = new JSONObject();
        bean1.put("name", "百度");
        bean1.put("url", "www.baidu.com");
        jsonArray.add(bean1);
        bean2.put("name", "新浪");
        bean2.put("url", "www.sina.com");
        jsonArray.add(bean2);
        jo.put("sites", jsonArray);
        System.out.println(jo.toJSONString());
    }
}
