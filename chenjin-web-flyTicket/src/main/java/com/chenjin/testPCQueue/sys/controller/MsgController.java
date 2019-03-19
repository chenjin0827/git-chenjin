package com.chenjin.testPCQueue.sys.controller;

import com.chenjin.testPCQueue.common.entity.DataGrid;
import com.chenjin.testPCQueue.common.entity.PageRequest;
import com.chenjin.testPCQueue.common.entity.Sort;
import com.chenjin.testPCQueue.common.framework.annotation.CurrentUser;
import com.chenjin.testPCQueue.common.web.controller.BaseController;
import com.chenjin.testPCQueue.sys.dto.Message;
import com.chenjin.testPCQueue.sys.entity.Msg;
import com.chenjin.testPCQueue.sys.entity.MsgItem;
import com.chenjin.testPCQueue.sys.entity.User;
import com.chenjin.testPCQueue.sys.service.IMsgItemService;
import com.chenjin.testPCQueue.sys.service.IMsgService;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/sys/message"})
public class MsgController extends BaseController
{

    @Resource
    private IMsgItemService msgItemService;

    @Resource
    private IMsgService msgService;

    @RequestMapping({""})
    public String home()
    {
        return "message/message";
    }

    @RequestMapping({"/sendmsg"})
    public String newmsg()
    {
        return "message/sendmsg";
    }

    @RequestMapping({"/recvpageByOrg"})
    @ResponseBody
    public DataGrid<MsgItem> recvpageByOrg(PageRequest pageable, @CurrentUser User currUser)
    {
        Long orgId = currUser.getOrganization().getId();
        Map query = new HashMap();
        query.put("t#sender_S_EQ", "0");
        query.put("t#organizationId_L_EQ", orgId);
        pageable.setQuery(query);
        pageable.setSort(new Sort(Sort.Direction.DESC, new String[] { "createDate" }));

        DataGrid page = this.msgItemService.query(currUser.getProjectCode(), pageable);
        return page;
    }

    @RequestMapping({"/recvpage"})
    @ResponseBody
    public DataGrid<MsgItem> recvpage(PageRequest pageable, MsgItem mrl, @CurrentUser User currUser)
    {
        mrl.setSender("0");

        DataGrid page = this.msgItemService.getMsglist(currUser.getProjectCode(), mrl, pageable);
        return page;
    }

    @RequestMapping({"/sendpage"})
    @ResponseBody
    public DataGrid<MsgItem> sendpage(PageRequest pageable, MsgItem mrl, @CurrentUser User currUser)
    {
        mrl.setSender("1");

        DataGrid page = this.msgItemService.getMsglist(currUser.getProjectCode(), mrl, pageable);
        return page;
    }

    @RequestMapping({"/draftpage"})
    @ResponseBody
    public DataGrid<Msg> draftpage(PageRequest pageable, Msg mh, @CurrentUser User currUser)
    {
        DataGrid page = this.msgService.getDraftMsg(currUser.getProjectCode(), mh, pageable);
        return page;
    }

    @RequestMapping(value={"/addDraft"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public Message addDraft(Msg msg, @CurrentUser User currUser)
    {
        Message message = new Message();
        try {
            msg.setIds("00");

            this.msgService.save(currUser.getProjectCode(), msg);
            message.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            message.setSuccess(false);
        }
        return message;
    }

    @RequestMapping(value={"/editaddDraft"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public Message edit_addDraft(Msg msg, @CurrentUser User currUser)
    {
        Message message = new Message();
        try {
            System.out.println(msg.toString());
            msg.setIds("00");

            this.msgService.update(currUser.getProjectCode(), msg);
            message.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            message.setSuccess(false);
        }

        return message;
    }

    @RequestMapping(value={"/send"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public Message send(Msg msg, String userIds, @CurrentUser User currUser)
    {
        Message message = new Message();
        try
        {
            message.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            message.setSuccess(false);
        }

        return message;
    }

    @RequestMapping(value={"/editsend"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public Message edit_send(Msg msg, String userIds, @CurrentUser User currUser)
    {
        Message message = new Message();
        try {
            this.msgService.sendFromDraft(currUser.getProjectCode(), msg, userIds, currUser);
            message.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            message.setSuccess(true);
        }

        return message;
    }

    @RequestMapping(value={"/delete"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public Message delete(Long id, @CurrentUser User user)
    {
        Message message = new Message();
        try {
            this.msgItemService.delete(user.getProjectCode(), id);
            message.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            message.setSuccess(false);
        }

        return message;
    }

    @RequestMapping(value={"/deletedraft"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public Message deletedraft(Long id, @CurrentUser User user)
    {
        Message message = new Message();
        try {
            this.msgService.delete(user.getProjectCode(), id);
            message.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            message.setSuccess(false);
        }

        return message;
    }

    protected void init(WebDataBinder binder)
    {
    }
}