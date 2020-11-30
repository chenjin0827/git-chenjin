package com.chenjin.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/*
 * --------------------自动刷CSDN博客访问量程序--------------------
 *csdn 刷流量基础版本
 * 将要刷访问量的博客地址(可以是CSDN主页、CSDN任意博客页)填写入23行的变量MYURL中，点击运行
 * 本程序【自动检测】该页所对应博主的【用户ID】，并访问该博主【用户ID】名下所有博客链接
 *
 * 仅供学习测试使用，不要真的用于刷访问量，因为不知道会不会被封号...
 */
public class UrlCrawBoke {

    public static String MYURL = "https://blog.csdn.net/chenjin_csdn/article/details/103643855";// 【填写】要爬取的首页

    public static String PATH = System.getProperty("user.dir");

    public static void main(String urlstr[]) throws IOException {

        List<String> urls = new ArrayList<String>();

        // ---------------------------------------------------截取用户ID---------------------------------------------------
        for(int i=0;i<1000;i++){

            exeuteTimes(urls);
        }
    }

    private static void exeuteTimes(List<String> urls) throws IOException {
        StringBuilder url = new StringBuilder(MYURL);
        int begin = url.lastIndexOf("csdn.net/");
        begin += 9;
        int end = url.indexOf("/", begin);
        if (end <= begin) {
            end = url.indexOf("?", begin);
            if (end <= begin) {
                end = url.length();
            }
        }

        String userId = url.substring(begin, end);

        if (userId.equals("blog")) {// 如果提交的是用户主页 重新截取
            begin = url.lastIndexOf("blog/");
            begin += 5;
            end = url.indexOf("/", begin);
            if (end <= begin)
                end = url.indexOf("?");
            userId = url.substring(begin, end);
        }
        System.out.println("用户名: " + userId);

        // ----------------------------------------------遍历每一页 获取文章链接----------------------------------------------
        final String homeUrl = "https://blog.csdn.net/" + userId + "/article/list/";// 后面加pageNum即可
        int totalPage = 0;
        InputStream is;
        String pageStr;
        StringBuilder curUrl = null;
        for (int i = 1; i < 100; i++) {
            curUrl = new StringBuilder(homeUrl);
            curUrl.append(i);
            System.out.println(curUrl);
            is = doGet(curUrl.toString());
            pageStr = inputStreamToString(is, "UTF-8");// 返回一个inputStream

            // 生成源代码文件
            PrintWriter pw = new PrintWriter(new FileWriter(PATH + "/Page" + i + ".txt"), true);
            pw.print(pageStr);
            pw.close();
            File filename = new File(PATH + "/Page" + i + ".txt");
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
            BufferedReader br = new BufferedReader(reader);// inputStream转化为BufferedReader 便于逐行读取

            System.out.println("正在获取第" + i + "页...");

            String line = null;
            while (true) {
                line = br.readLine();
                if (line == null || line.indexOf("</main>") != -1)// 读到末尾 或者 main标签结束
                    break;
                else {
                    if (line.indexOf("http") != -1 && line.indexOf("/details/") != -1 && line.indexOf("yoyo_liyy") == -1
                            && line.indexOf(userId) != -1) {// 条件:包含http, /details/, userId, 不包含yoyo_liyy
                        String tempUrl = new String(line);
                        tempUrl = tempUrl.trim();
                        // System.out.println("t=" + tempUrl);
                        tempUrl = tempUrl.substring(tempUrl.indexOf("http"), tempUrl.indexOf("/details/") + 17);
                        if (urls.size() == 0 || (urls.size() != 0 && !(urls.get(urls.size() - 1).equals(tempUrl)))) {// 空，或者非空且没有有重复
                            // 写入
                            urls.add(tempUrl);
                            System.out.println(tempUrl);
                        }
                    }
                }
            }

            br.close();

            if (pageStr.lastIndexOf("空空如也") != -1) {
                System.out.println("No This Page!");
                break;
            } else {
                System.out.println("Success~");
            }
            totalPage = i;
        }
        System.out.println("总页数为: " + totalPage);

        // ---------------------------------------------------访问每个链接---------------------------------------------------
        for (int i = 0; i < urls.size(); i++) {
            doGet(urls.get(i));
            System.out.println("成功访问第" + (i + 1) + "个链接,共" + urls.size() + "个:" + urls.get(i));
        }

        // ---------------------------------------------------程序结束---------------------------------------------------
        System.out.println("运行完毕，成功增加访问数：" + urls.size());
    }

    public static InputStream doGet(String urlstr) throws IOException {
        URL url = new URL(urlstr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
        InputStream inputStream = conn.getInputStream();
        return inputStream;
    }

    public static String inputStreamToString(InputStream is, String charset) throws IOException {
        byte[] bytes = new byte[1024];
        int byteLength = 0;
        StringBuffer sb = new StringBuffer();
        while ((byteLength = is.read(bytes)) != -1) {
            sb.append(new String(bytes, 0, byteLength, charset));
        }
        return sb.toString();
    }
}
