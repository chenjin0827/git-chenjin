package com.etgyd.filebeatlog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;



/**
 * 定时任务
 * Created by huangshengqing on 2018/6/28.
 *
 * @version 1.0
 */
@EnableScheduling
@Component
public class SchameJob {
    int i=0;

    Logger logger=Logger.getLogger(this.getClass());
    //每30分钟执行
    @Scheduled(cron = "*/10 * * * * *")
    public void every30Min() throws Exception {
        String test="my name is \" china \"";
//        LoggerUtil.info(this.getClass(), "测试项目，每10秒定时程序"+(i++));
        logger.info("测试项目，每10秒定时程序"+(i++));

    }


    public static void main(String[] args) throws Exception {
//        String json="{\"name\":\"abcd\"}";
//        String test="my name is \" china \""+json;
//        System.out.println(test.indexOf("\""));
//
//        Pattern p2 = Pattern.compile("\"");
//        Matcher m2 = p2.matcher(test);
//        String value = m2.replaceAll("\\\"");
//        System.out.println(value);

        String lineSeparator = System.getProperty("line.separator");
        System.out.println(lineSeparator);
    }
}
