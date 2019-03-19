package com.chenjin.testPCQueue.sys.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JsonUtil
{
    public static void JsonToHashMap(JSONObject jsonData, Map<String, Object> rstList, String[] params)
    {
        try
        {
            Iterator keyStr = jsonData.keySet().iterator();

            while (keyStr.hasNext()) {
                String key1 = ((String)keyStr.next()).trim();
                if ((jsonData.get(key1) instanceof JSONObject)) {
                    HashMap mapObj = new HashMap();
                    JsonToHashMap((JSONObject)jsonData.get(key1), mapObj, params);
                    rstList.put(key1, mapObj);
                } else if ((jsonData.get(key1) instanceof JSONArray)) {
                    ArrayList arrayList = new ArrayList();
                    JSONArrayToHashMap((JSONArray)jsonData.get(key1), arrayList, params);
                    rstList.put(key1, arrayList);
                } else {
                    JsonToHashMap(key1, jsonData.get(key1), rstList);
                }
            }

            if ((params != null) && (params.length == 2)) {
                rstList.put(params[0], params[1]);
            }

            if ((params != null) && (params.length == 4)) {
                rstList.put(params[0], params[1]);
                rstList.put(params[2], params[3]);
            }
        } catch (JSONException var6) {
            var6.printStackTrace();
        }
    }

    public static void JSONArrayToHashMap(JSONArray jsonarray, List<Map<String, Object>> rstList, String[] params)
    {
        try {
            for (int i = 0; i < jsonarray.size(); i++)
                if ((jsonarray.get(i) instanceof JSONObject)) {
                    HashMap mapObj = new HashMap();
                    JsonToHashMap((JSONObject)jsonarray.get(i), mapObj, params);
                    rstList.add(mapObj);
                }
        }
        catch (JSONException var5) {
            var5.printStackTrace();
        }
    }

    public static void JsonToHashMap(String key, Object value, Map<String, Object> rstList)
    {
        if ((value instanceof String))
            rstList.put(key, (String)value);
        else
            rstList.put(key, value);
    }

    public static String tranObjectToJsonStr(Object obj)
    {
        return JSONObject.toJSONString(obj);
    }

    public static <T> T tranjsonStrToObject(String jsonStr, Class<T> clazz) {
        return JSONObject.parseObject(jsonStr, clazz);
    }

    public static <T> List<T> tranjsonStrToArray(String jsonStr, Class<T> clazz) {
        return JSONObject.parseArray(jsonStr, clazz);
    }

    public static JSONObject tranjsonStrToJSONObject(String jsonStr) {
        return JSONObject.parseObject(jsonStr);
    }
}