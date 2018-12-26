package com.chenjin.common.ca;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CAProperties
{
    public static String SZCA_SIGN_HTTP = "";

    public static String SZCA_SIGN_OUT_HTTP = "";

    public static String SGL_PDFSEND_HTTP = "";

    public static String SGL_PDFREAD_OUT_HTTP = "";

    public static String SGL_PDFREAD_IN_HTTP = "";

    public static String SGL_PDFREAD_IN_HTTP_0 = "";

    public static String SGL_PDFREAD_IN_HTTP_1 = "";

    public static String SGL_PDFREAD_IN_HTTP_2 = "";

    public static String SGLIOCODE = "";

    public static String BJCA_PDF_HOST = "";

    public static String BJCA_PDF_PORT = "";

    static
    {
        Properties prop = new Properties();
        InputStream in = null;
        try {
            in = CAProperties.class.getResourceAsStream("/CA.properties");
            prop.load(in);

            SZCA_SIGN_HTTP = prop.getProperty("SZCA_SIGN_HTTP", "").trim();
            SZCA_SIGN_OUT_HTTP = prop.getProperty("SZCA_SIGN_OUT_HTTP", "").trim();
            SGL_PDFSEND_HTTP = prop.getProperty("SGL_PDFSEND_HTTP", "").trim();
            SGL_PDFREAD_OUT_HTTP = prop.getProperty("SGL_PDFREAD_OUT_HTTP", "").trim();
            SGL_PDFREAD_IN_HTTP = prop.getProperty("SGL_PDFREAD_IN_HTTP", "").trim();
            SGL_PDFREAD_IN_HTTP_0 = prop.getProperty("SGL_PDFREAD_IN_HTTP_0", "").trim();
            SGL_PDFREAD_IN_HTTP_1 = prop.getProperty("SGL_PDFREAD_IN_HTTP_1", "").trim();
            SGL_PDFREAD_IN_HTTP_2 = prop.getProperty("SGL_PDFREAD_IN_HTTP_2", "").trim();
            SGLIOCODE = prop.getProperty("SGLIOCODE", "").trim();
            BJCA_PDF_HOST = prop.getProperty("BJCA_PDF_HOST", "").trim();
            BJCA_PDF_PORT = prop.getProperty("BJCA_PDF_PORT", "").trim();
        }
        catch (IOException e) {
            e.printStackTrace();

            if (in != null)
                try {
                    in.close();
                }
                catch (IOException localIOException1)
                {
                }
        }
        finally
        {
            if (in != null)
                try {
                    in.close();
                }
                catch (IOException localIOException2)
                {
                }
        }
    }
}