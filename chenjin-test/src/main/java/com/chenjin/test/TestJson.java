package com.chenjin.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class TestJson {
    public static void main(String[] args) {
        JSONObject jo = new JSONObject();
        JSONArray resourceList = new JSONArray();
        JSONObject jsonObject1 = new JSONObject();


        jsonObject1.put("code", "0");
        jsonObject1.put("desc", "成功");
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("keyword", "M90110100");
        jsonObject2.put("pid", 90110000);
        jsonObject2.put("resLevel", 3);
        jsonObject2.put("resType", 1);
        jsonObject2.put("resid", 90110100);
        jsonObject2.put("resname", "功能管理");
        jsonObject2.put("treelayer", "010101000000");
        jsonObject2.put("url", "menuMain.action");
        resourceList.add(jsonObject2);
        JSONObject jsonObject3 = new JSONObject();
        jsonObject3.put("keyword", "M90110200");
        jsonObject3.put("pid", 90110000);
        jsonObject3.put("resLevel", 3);
        jsonObject3.put("resType", 1);
        jsonObject3.put("resid", 90110200);
        jsonObject3.put("resname", "角色管理");
        jsonObject3.put("treelayer", "010102000000");
        jsonObject3.put("url", "roleList.action");
        resourceList.add(jsonObject3);
        jsonObject1.put("resourceList", resourceList);
        JSONArray roleList = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("roleid",9011002);
        jsonObject.put("rolename","审核管理员(权限)");
        roleList.add(jsonObject);
        JSONObject jsonObject4 = new JSONObject();
        jsonObject4.put("roleid",9011001);
        jsonObject4.put("rolename","管理员(权限)");
        roleList.add(jsonObject4);
        jsonObject1.put("roleList",roleList);

        JSONObject jsonObject5 = new JSONObject();
        jsonObject5.put("email","");
        jsonObject5.put("gender", "");
        jsonObject5.put("jgdm", "");
        jsonObject5.put("jgmc", "");
        jsonObject5.put("loginname", "auth_admin");
        jsonObject5.put("mobilephone", "");
        jsonObject5.put("psw", "12ff5533ffb24a613261a1537c99e6f1");
        jsonObject5.put("userid", 9011001);
        jsonObject5.put("username", "权限管理员");
        jsonObject1.put("user",jsonObject5);
        jo.put("Result", jsonObject1);
        System.out.println(jo.toJSONString());
    }
}
