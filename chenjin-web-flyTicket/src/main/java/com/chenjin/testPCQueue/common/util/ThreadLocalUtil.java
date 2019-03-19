package com.chenjin.testPCQueue.common.util;

public class ThreadLocalUtil
{
    private static final ThreadLocal<String> dataSources = new ThreadLocal();
    private static final ThreadLocal<String> sessionFactorys = new ThreadLocal();
    private static final ThreadLocal<String> sysUser = new ThreadLocal();
    private static final ThreadLocal<String> sysName = new ThreadLocal();

    public static void setDataSource(String name) {
        dataSources.set(name);
    }
    public static String getDataSource() {
        return (String)dataSources.get();
    }
    public static void clearDataSource() {
        dataSources.remove();
    }

    public static void setSessionFactory(String name) {
        sessionFactorys.set(name);
    }
    public static String getSessionFactory() {
        return (String)sessionFactorys.get();
    }
    public static void clearSessionFactory() {
        sessionFactorys.remove();
    }

    public static void setSysUser(String name) {
        sysUser.set(name);
    }
    public static String getSysUser() {
        return (String)sysUser.get();
    }
    public static void clearSysUser() {
        sysUser.remove();
    }

    public static void setSysName(String name) {
        sysName.set(name);
    }
    public static String getSysName() {
        return (String)sysName.get();
    }
    public static void clearSysName() {
        sysName.remove();
    }
}