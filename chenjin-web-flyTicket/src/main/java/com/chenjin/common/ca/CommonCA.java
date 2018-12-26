package com.chenjin.common.ca;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CommonCA
{
    public static Map<String, String> checkSign(String CAName, String userCert, String random, String userSignedData, String userIP)
    {
        Map resultMap = new HashMap();
        resultMap.put("code", "false");
        try {
            ICompanyCA commonCA = (ICompanyCA)Class.forName("com.chenjin.common.ca." + CAName).newInstance();
            resultMap = commonCA.checkSign(userCert, random, userSignedData, userIP);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    public static File addSignLocal(String CAName, byte[] pdf, String ruleNum, String pdfName)
    {
        File url = null;
        try {
            ICompanyCA commonCA = (ICompanyCA)Class.forName("com.chenjin.common.ca." + CAName).newInstance();
            url = commonCA.addSignLocal(pdf, ruleNum, pdfName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String addSignWeb(String CAName, String wj, String pdfName, String ip)
    {
        String url = "";
        try {
            ICompanyCA commonCA = (ICompanyCA)Class.forName("com.chenjin.common.ca." + CAName).newInstance();
            url = commonCA.addSignWeb(wj, pdfName, ip);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }
}