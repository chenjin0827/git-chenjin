package com.chenjin.sys.task;

import com.chenjin.common.cache.dao.TaskLockDAO;
import com.chenjin.sys.service.IUserService;
import java.io.PrintStream;
import javax.annotation.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UserUnlockTaskJob
{

    @Resource
    private TaskLockDAO taskLockDAO;

    @Resource
    private IUserService userService;

    @Scheduled(cron="0/180 * * * * ?")
    public void carry()
    {
        System.out.println("开始执行-------------UserUnlockTaskJob");
        long begin = System.currentTimeMillis();
        if (!this.taskLockDAO.lock("UserUnlockTaskJob").booleanValue()) {
            return;
        }
        try
        {
            this.userService.unlock();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.taskLockDAO.unlock("UserUnlockTaskJob");
        }
        long end = System.currentTimeMillis() - begin;
        System.out.println("完成执行-------------UserUnlockTaskJob 耗时：" + end + "毫秒");
    }
}