package com.chenjin.thread.msg;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用多线程完成分批发送短信
 */
public class ThreadMsgMain {
    public static void main(String[] args) {
        // 1.初始化用户数据
        List<UserEntity> listUserEntity = init();
        // 2.计算创建创建多少个线程并且每一个线程需要执行“分批发送短信用户”
        // 每一个线程分批跑多少
        int userThreadPage = 50;
        // 计算所有线程数
        List<List<UserEntity>> splitUserList = ListUtils.splitList(listUserEntity, userThreadPage);
        int threadSaze = splitUserList.size();
        for (int i = 0; i < threadSaze; i++) {
            List<UserEntity> list = splitUserList.get(i);
            UserThread userThread = new UserThread(list);
            // 3.执行任务发送短信
            userThread.start();
        }

    }

    /**
     * @methodDesc: 功能描述:(初始化数据)
     * @returnType: void
     */
    public static List<UserEntity> init() {
        List<UserEntity> list = new ArrayList<>();
        for (int i = 1; i <= 140; i++) {
            UserEntity userEntity = new UserEntity();
            userEntity.setUserId("userId" + i);
            userEntity.setUserName("userName" + i);
            list.add(userEntity);
        }
        return list;
    }
}
