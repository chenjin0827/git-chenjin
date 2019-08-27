package com.chenjin.test;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

public class TestJson {
public static void main(String []args){
    Map<String, String> hashMap = new HashMap<>();
    hashMap.put("aaa","aaa");
    hashMap.put("bbb","bbb");
    hashMap.put("ccc","ccc");
    String s = JSON.toJSONString(hashMap);
    System.out.println("s====="+s);
}
}
