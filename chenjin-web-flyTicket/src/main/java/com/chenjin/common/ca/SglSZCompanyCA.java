package com.chenjin.common.ca;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chenjin.common.util.HttpRequestUtil;
import java.io.File;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SglSZCompanyCA
        implements ICompanyCA
{
    private static Logger logger = LoggerFactory.getLogger(SglSZCompanyCA.class);

    public Map<String, String> checkSign(String userCert, String random, String userSignedData, String userIP) {
        String url = CAProperties.SZCA_SIGN_OUT_HTTP;
        if ((userIP.startsWith("6.6")) || (userIP.startsWith("100.10")) || (userIP.startsWith("200.10"))) {
            url = CAProperties.SZCA_SIGN_HTTP;
        }
        String result = HttpRequestUtil.verSignData(userCert, random, userSignedData, url);
        logger.info("result=" + result);
        Map resultMap = new HashMap();
        JSONObject obj = JSON.parseObject(result);
        resultMap.put("code", obj.getString("code"));
        resultMap.put("msg", obj.getString("msg"));
        resultMap.put("oid", obj.getString("oid"));

        return resultMap;
    }

    public File addSignLocal(byte[] pdf, String ruleNum, String pdfName)
    {
        return null;
    }

    public String addSignWeb(String wj, String pdfName, String userIP)
    {
        String url = "";
        String rlt = HttpRequestUtil.sgl_pdfSend(wj, pdfName, CAProperties.SGL_PDFSEND_HTTP);

        JSONObject jo = JSON.parseObject(rlt);
        if (jo.getString("Status").equals("y")) {
            System.out.println("userIP====" + userIP);
            url = CAProperties.SGL_PDFREAD_OUT_HTTP;
            if (userIP.startsWith("6.6"))
                url = CAProperties.SGL_PDFREAD_IN_HTTP_0;
            else if (userIP.startsWith("200.10"))
                url = CAProperties.SGL_PDFREAD_IN_HTTP_1;
            else if (userIP.startsWith("100.10")) {
                url = CAProperties.SGL_PDFREAD_IN_HTTP_2;
            }
        }
        return url;
    }
}