package com.chenjin.testPCQueue.common.util.wx;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.security.SecureRandom;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public class WxApiCall
{
    public static String sendGet(String url)
    {
        StringBuffer buffer = null;
        try
        {
            URL realUrl = new URL(url);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection)realUrl.openConnection();

            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new SecureRandom());

            SSLSocketFactory ssf = sslContext.getSocketFactory();

            httpUrlConn.setSSLSocketFactory(ssf);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setRequestMethod("GET");

            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            buffer = new StringBuffer();
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            httpUrlConn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    public static String sendGet(String url, Object param)
    {
        String result = "";
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            URL realUrl = new URL(url);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection)realUrl.openConnection();

            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new SecureRandom());

            SSLSocketFactory ssf = sslContext.getSocketFactory();

            httpUrlConn.setSSLSocketFactory(ssf);
            httpUrlConn.setDoOutput(true);

            httpUrlConn.connect();
            out = new PrintWriter(httpUrlConn.getOutputStream());

            out.print(param);

            in = new BufferedReader(new InputStreamReader(httpUrlConn
                    .getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null)
                result = new StringBuilder().append(result).append(line).toString();
        }
        catch (Exception e) {
            System.out.println(new StringBuilder().append("发送GET请求出现异常！").append(e).toString());
            e.printStackTrace();
        }
        finally {
            try {
                if (in != null)
                    in.close();
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        System.out.println(result);
        return result;
    }

    public static String sendPost(String url, Object param)
    {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection)realUrl.openConnection();

            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new SecureRandom());

            SSLSocketFactory ssf = sslContext.getSocketFactory();

            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);

            out = new PrintWriter(new OutputStreamWriter(httpUrlConn.getOutputStream(), "utf-8"));

            out.print(param);

            out.flush();

            in = new BufferedReader(new InputStreamReader(httpUrlConn
                    .getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null)
                result = new StringBuilder().append(result).append(line).toString();
        }
        catch (Exception e)
        {
            System.out.println(new StringBuilder().append("发送 POST 请求出现异常！").append(e).toString());
            e.printStackTrace();
        }
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null)
                    in.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static String sendPost(String path, File file) {
        BufferedReader reader = null;
        String result = null;
        try {
            URL realUrl = new URL(path);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection)realUrl.openConnection();

            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new SecureRandom());

            SSLSocketFactory ssf = sslContext.getSocketFactory();

            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoInput(true);
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setUseCaches(false);

            httpUrlConn.setRequestProperty("Connection", "Keep-Alive");
            httpUrlConn.setRequestProperty("Charset", "UTF-8");

            String BOUNDARY = new StringBuilder().append("----------").append(System.currentTimeMillis()).toString();
            httpUrlConn.setRequestProperty("Content-Type", new StringBuilder().append("multipart/form-data; boundary=").append(BOUNDARY).toString());

            StringBuilder sb = new StringBuilder();
            sb.append("--");
            sb.append(BOUNDARY);
            sb.append("\r\n");
            sb.append(new StringBuilder().append("Content-Disposition: form-data;name=\"media\";filelength=\"").append(file.length()).append("\";filename=\"")
                    .append(file
                            .getName()).append("\"\r\n").toString());
            sb.append("Content-Type:application/octet-stream\r\n\r\n");

            byte[] head = sb.toString().getBytes("utf-8");

            OutputStream out = new DataOutputStream(httpUrlConn.getOutputStream());

            out.write(head);

            DataInputStream in = new DataInputStream(new FileInputStream(file));
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            in.close();

            byte[] foot = new StringBuilder().append("\r\n--").append(BOUNDARY).append("--\r\n").toString().getBytes("utf-8");
            out.write(foot);
            out.flush();
            out.close();
            StringBuffer buffer = new StringBuffer();
            reader = new BufferedReader(new InputStreamReader(httpUrlConn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (result == null)
                result = buffer.toString();
        }
        catch (Exception e) {
            System.out.println(new StringBuilder().append("发送POST请求出现异常！").append(e).toString());
            e.printStackTrace();
        } finally {
            try {
                if (reader != null)
                    reader.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}