package com.chenjin.common.util.wx;

public class WxApi
{
    private static String wx_APPID = "";
    private static String wx_APPSECRET = "";

    public static String miniOpenId(String code)
    {
        String userUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=" + wx_APPID + "&secret=" + wx_APPSECRET + "&js_code=" + code + "&grant_type=authorization_code";
        return WxApiCall.sendGet(userUrl);
    }

    public static String getWx_APPID() {
        return wx_APPID;
    }

    public static void setWx_APPID(String wx_APPID) {
        wx_APPID = wx_APPID;
    }

    public static String getWx_APPSECRET() {
        return wx_APPSECRET;
    }

    public static void setWx_APPSECRET(String wx_APPSECRET) {
        wx_APPSECRET = wx_APPSECRET;
    }
}