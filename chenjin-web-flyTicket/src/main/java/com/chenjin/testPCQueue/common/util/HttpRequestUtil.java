package com.chenjin.testPCQueue.common.util;

import net.sf.json.JSONObject;

public class HttpRequestUtil
{
    public static String verSignData(String cert, String indata, String signdata, String url)
    {
        try
        {
            JSONObject send = new JSONObject();
            send.put("cert", cert);
            send.put("indata", indata);
            send.put("signdata", signdata);

            String result = HttpClientPost.doPost(url, send.toString().getBytes("UTF-8"), "application/json; charset=utf-8");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String sgl_pdfSend(String wj, String keyId, String url) {
        try {
            JSONObject send = new JSONObject();
            send.put("BaseForFile", wj);
            send.put("FileId", keyId);

            String result = HttpClientPost.doPost(url, send.toString().getBytes("UTF-8"), "application/json; charset=utf-8");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}