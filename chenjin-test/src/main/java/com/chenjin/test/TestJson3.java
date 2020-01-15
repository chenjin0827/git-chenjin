package com.chenjin.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class TestJson3 {
    static String s = "{\"Result\":{\"code\":\"0\",\"desc\":\"成功\",\"resourceList\":[{\"keyword\":\"M90110100\",\"pid\":90110000,\"resLevel\":3,\"resType\":1,\"resid\":90110100,\"resname\":\"功能管理\",\"treelayer\":\"010101000000\",\"url\":\"menuMain.action\"},{\"keyword\":\"M90110200\",\"pid\":90110000,\"resLevel\":3,\"resType\":1,\"resid\":90110200,\"resname\":\"角色管理\",\"treelayer\":\"010102000000\",\"url\":\"roleList.action\"}],\"roleList\":[{\"roleid\":9011002,\"rolename\":\"审核管理员(权限)\"},{\"roleid\":9011001,\"rolename\":\"管理员(权限)\"}],\"user\":{\"email\":\"\",\"gender\":\"\",\"jgdm\":\"\",\"jgmc\":\"\",\"loginname\":\"auth_admin\",\"mobilephone\":\"\",\"psw\":\"12ff5533ffb24a613261a1537c99e6f1\",\"userid\":9011001,\"username\":\"权限管理员\"}}}\n";

    public static void main(String[] args) {

        JSONObject jsonObject = JSONObject.parseObject(s);
        JSONObject result = jsonObject.getJSONObject("Result");
        JSONObject user = result.getJSONObject("user");
        String loginname = user.getString("loginname");
        System.out.println(loginname);
    }
}
