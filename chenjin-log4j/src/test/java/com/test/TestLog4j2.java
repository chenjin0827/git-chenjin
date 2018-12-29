package com.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//trace<debug<info<warn<error<fatal 顺序
public class TestLog4j2 {
    public static void main(String[] args) {
        Logger logger = LogManager.getLogger(TestLog4j2.class);
        for (int i=0;i<10000;i++){
            logger.trace("trace level");
            logger.debug("debug level");
            logger.info("info level");
            logger.error("error level");
            logger.fatal("fatal level");
        }
    }
}
