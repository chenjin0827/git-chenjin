package com.chenjin.jsonandxml.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JsonTest {
    static String jsonStr = "{\"sites\": [{\"name\": \"蚂蚁课堂\",\"url\": \"www.itmayiedu.com\"},{\"name\": \"每特教育\",\"url\": \"http://meiteedu.com/\"}]}";

    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = jsonObject.parseObject(jsonStr);
        JSONArray sites = jsonObject1.getJSONArray("sites");
        for (Object s : sites) {
            JSONObject s1 = (JSONObject) s;
            String name = s1.getString("name");
            String url = s1.getString("url");
            System.out.println(name + "----" + url);
        }
        System.out.println(jsonObject1);
    }
}
