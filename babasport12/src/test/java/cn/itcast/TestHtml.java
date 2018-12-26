package cn.itcast;

import org.dom4j.DocumentException;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestHtml {
    public static void main(String[] args) throws IOException, DocumentException {
        // 定义正则表达式来搜索中文字符的转义符号
        Pattern compile = Pattern.compile("&#.*?;");
        // 测试用中文字符
        String sourceString = "C:/集团天c津大唐国际盘山发电有限责任公司";
        Matcher matcher = compile.matcher(sourceString);
        // 循环搜索 并转换 替换
        while (matcher.find()) {
            String group = matcher.group();
            // 获得16进制的码
            String hexcode = "0" + group.replaceAll("(&#|;)", "");
            // 字符串形式的16进制码转成int并转成char 并替换到源串中
            sourceString = sourceString.replaceAll(group, (char) Integer.decode(hexcode).intValue() + "");
        }
        System.out.println(sourceString);
    }

}
