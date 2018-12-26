package com.chenjin.common.util;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.lang3.StringUtils;

public class SHA1
{
    private static String byteArrayToHex(byte[] byteArray)
    {
        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] resultCharArray = new char[byteArray.length * 2];
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[(index++)] = hexDigits[(b >>> 4 & 0xF)];
            resultCharArray[(index++)] = hexDigits[(b & 0xF)];
        }
        return new String(resultCharArray);
    }

    public static String getMessageDigest(String str, String encName)
    {
        byte[] digest = null;
        if (StringUtils.isBlank(encName))
            encName = "SHA-1";
        try
        {
            MessageDigest md = MessageDigest.getInstance(encName);
            md.update(str.getBytes("UTF-8"));
            digest = md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        return byteArrayToHex(digest);
    }

    public static boolean checkSign(String iocode, String data, String sign)
    {
        String sSign = getMessageDigest(iocode + data, "SHA-1");
        System.out.println("服务器签名=" + sSign);

        return sign.equals(sSign);
    }
}