package com.chenjin.testPCQueue.sys.controller;

import com.chenjin.testPCQueue.common.framework.annotation.CurrentUser;
import com.chenjin.testPCQueue.common.web.controller.BaseController;
import com.chenjin.testPCQueue.sys.dto.Message;
import com.chenjin.testPCQueue.sys.entity.Group;
import com.chenjin.testPCQueue.sys.entity.GroupUser;
import com.chenjin.testPCQueue.sys.entity.User;
import com.chenjin.testPCQueue.sys.service.IGroupUserService;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/sys/groupuser"})
public class GroupUserController extends BaseController
{

    @Resource
    private IGroupUserService groupUserService;
    private static Logger logger = LoggerFactory.getLogger(GroupUserController.class);

    @RequestMapping(value={"/add"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public Message add(Long userId, Long groupId, @CurrentUser User user)
    {
        Message message = new Message();
        System.out.println(userId + "," + groupId);
        try {
            GroupUser guold = this.groupUserService.findByKey(user.getProjectCode(), userId, groupId);
            if (guold == null) {
                GroupUser gu = new GroupUser();
                User u = new User();
                u.setId(userId);
                Group g = new Group();
                g.setId(groupId);
                gu.setUser(u);
                gu.setGroup(g);
                this.groupUserService.save(user.getProjectCode(), gu);
                logger.info(user.getEmpId() + "添加了" + g.getName() + "组的新账号[" + u.getEmpId() + "]");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            message.setSuccess(false);
        }
        return message;
    }

    @RequestMapping(value={"/del"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public Message del(Long userId, Long groupId, @CurrentUser User currentUser)
    {
        System.out.println(userId + "," + groupId);
        Message message = new Message();
        try {
            if (!currentUser.getId().equals(userId)) {
                GroupUser gu = this.groupUserService.findByKey(currentUser.getProjectCode(), userId, groupId);
                if (gu != null) {
                    this.groupUserService.delete(currentUser.getProjectCode(), gu);
                    logger.info(currentUser.getEmpId() + "添加了" + gu.getGroup().getName() + "组的新账号[" + gu.getUser().getEmpId() + "]");
                }
            }
            else {
                throw new Exception("无法将自己从组里移除");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            message.setSuccess(false);
            message.setMsg(e.getMessage());
        }

        return message;
    }

    protected void init(WebDataBinder binder)
    {
    }
}