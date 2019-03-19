package com.chenjin.testPCQueue.sys.controller;

import com.alibaba.fastjson.JSONObject;
import com.chenjin.testPCQueue.common.cache.dao.VerifyCodeDAO;
import com.chenjin.testPCQueue.commons.CommonProperties;
import com.chenjin.testPCQueue.sys.common.JsonUtil;
import com.chenjin.testPCQueue.sys.entity.User;
import com.chenjin.testPCQueue.sys.service.IUserService;
import com.chenjin.testPCQueue.sys.service.realm.ShiroMyToken;
import com.web.security.CommonSecurity;
import com.web.vo.SecurityModel;

import javax.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/thirdLogin"})
public class ThirdLoginController
{

    @Resource
    private IUserService userService;

    @Resource
    private VerifyCodeDAO verifyCodeDAO;

    @RequestMapping({"/zs"})
    public String zsLogin(String appName, String data, String sign, String timeStamp, String unique)
            throws Exception
    {
        System.out.println("中山单点登录....");
        try {
            System.out.println("appName=" + appName);
            System.out.println("data=" + data);
            System.out.println("sign=" + sign);
            System.out.println("timeStamp=" + timeStamp);
            System.out.println("unique=" + unique);

            String d = checkSignZS(appName, data, sign, timeStamp, unique);
            System.out.println("idcard=" + d);
            User user = this.userService.getByIdcode(d);
            if (user == null) {
                throw new Exception("身份验证失败");
            }

            doLogin(user.getEmpId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/home/index.htmlx";
    }

    private String checkSignZS(String appName, String data, String sign, String timeStamp, String unique) throws Exception
    {
        String security = CommonProperties.SECURITY;
        System.out.println("security=" + security);

        JSONObject jo = new JSONObject();

        jo.put("appName", appName);
        jo.put("data", data);
        jo.put("sign", sign);
        jo.put("timeStamp", timeStamp);
        jo.put("unique", unique);
        System.out.println("jo.toJSONString() = " + jo.toJSONString());

        String d = new CommonSecurity(appName, security).decrypt(jo.toJSONString());
        System.out.println("mingwen.d=" + d);
        if ((d != null) && (d.indexOf("idcard=") > 0)) {
            d = d.trim();
            d = d.substring(d.indexOf("idcard=") + 7, d.length() - 1);
        }
        System.out.println("jiexi.d = " + d);
        return d;
    }

    private void doLogin(String userName)
    {
        System.out.println(userName + "开始登录...");
        Subject sub = SecurityUtils.getSubject();
        ShiroMyToken token = new ShiroMyToken(userName, "", "", "1236", false, "", "", "", "");
        sub.login(token);
    }

    public static void main(String[] args) {
        String d = " {idcard=3302-06198401111716}";
        System.out.println("mingwen.d=" + d);
        if ((d != null) && (d.indexOf("idcard=") > 0)) {
            d = d.trim();
            d = d.substring(d.indexOf("idcard=") + 7, d.length() - 1);
        }
        System.out.println("jiexi.d = " + d);

        String appName = "hdpt";
        String security = "YzuDBFs3Vo";
        String idCard = "330206198401111716";

        SecurityModel sm = new CommonSecurity(appName, security).encrypt(idCard);

        System.out.println("sm.getData()=" + sm.getData());
        String jsStr = JsonUtil.tranObjectToJsonStr(sm);
        System.out.println(jsStr);

        String odata = new CommonSecurity(appName, security).decrypt(jsStr);
        System.out.println("odata=" + odata);
    }
}