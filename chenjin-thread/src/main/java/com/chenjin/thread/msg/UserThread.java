package com.chenjin.thread.msg;

import java.util.List;

public class UserThread extends Thread {
    private List<UserEntity> list;

    /**
     * 通过构造函数 传入每个线程需要执行的发送短信内容
     *
     * @param list
     */
    public UserThread(List<UserEntity> list) {
        this.list = list;
    }

    /**
     * @methodDesc: 功能描述:(分批发送短信)
     * @returnType: void
     */
    public void run() {
        for (UserEntity userEntity : list) {
            System.out.println("threadName:" + Thread.currentThread().getName() + "-学员编号:" + userEntity.getUserId()
                    + "---学员名称:" + userEntity.getUserName());
            // 调用发送短信具体代码
        }
    }
}
