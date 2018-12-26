package com.chenjin.sys.controller;

import com.chenjin.common.entity.DataGrid;
import com.chenjin.common.entity.PageRequest;
import com.chenjin.common.entity.Sort;
import com.chenjin.common.entity.Sort.Direction;
import com.chenjin.common.framework.annotation.CurrentUser;
import com.chenjin.common.web.controller.BaseController;
import com.chenjin.sys.dto.Message;
import com.chenjin.sys.entity.Attribute;
import com.chenjin.sys.entity.AttributeItem;
import com.chenjin.sys.entity.User;
import com.chenjin.sys.service.IAttributeItemService;
import com.chenjin.sys.service.IAttributeService;
import com.chenjin.sys.service.IUserService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/sys/attributeItem"})
public class AttributeItemController extends BaseController
{

    @Resource(name="userService")
    private IUserService userService;

    @Resource(name="attributeItemService")
    private IAttributeItemService attributeItemService;

    @Resource(name="attributeService")
    private IAttributeService attributeService;

    @RequestMapping({""})
    public String home()
    {
        return "sys/attribute/list";
    }

    @RequestMapping({"/page"})
    @ResponseBody
    public DataGrid<AttributeItem> homePage(PageRequest pageable, @CurrentUser User user)
    {
        DataGrid page = this.attributeItemService.query(user.getProjectCode(), pageable);
        return page;
    }

    @RequestMapping(value={"/add"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public String add()
    {
        return "sys/attribute/subadd";
    }

    @RequestMapping(value={"/add"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public Message add(AttributeItem attributeItem, Long pid, @CurrentUser User user)
    {
        Message message = new Message();
        try
        {
            Attribute attribute = (Attribute)this.attributeService.getById(user.getProjectCode(), pid);
            AttributeItem obj = this.attributeItemService.queryByAttrAndItemNo(user.getProjectCode(), attribute.getAttributeNo(), attributeItem.getField1());
            if (obj == null) {
                attributeItem.setAttribute(attribute);
                this.attributeItemService.save(user.getProjectCode(), attributeItem);
                message.setMsg("代号" + attributeItem.getField1() + "添加成功");
            } else {
                message.setSuccess(false);
                message.setMsg("代号" + attributeItem.getField1() + "已经添加，不能重复添加");
            }
        } catch (Exception e) {
            e.printStackTrace();
            message.setSuccess(false);
            message.setMsg(e.getMessage());
        }

        return message;
    }

    @RequestMapping(value={"/edit"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public String edit()
    {
        return "sys/attribute/subedit";
    }

    @RequestMapping(value={"/edit"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public Message edit(AttributeItem attributeItem, @CurrentUser User user)
    {
        Message message = new Message();
        try
        {
            this.attributeItemService.updateWithInclude(user.getProjectCode(), attributeItem, new String[] { "field1", "field2", "field3", "field4" });
            message.setMsg("代号" + attributeItem.getField1() + "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            message.setSuccess(false);
            message.setMsg(e.getMessage());
        }

        return message;
    }

    @RequestMapping(value={"/delete"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public Message delete(Long id, @CurrentUser User user)
    {
        Message message = new Message();
        try
        {
            AttributeItem attributeItem = (AttributeItem)this.attributeItemService.getById(user.getProjectCode(), id);
            this.attributeItemService.delete(user.getProjectCode(), id);
            message.setMsg("代号" + attributeItem.getField1() + "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            message.setSuccess(false);
            message.setMsg(e.getMessage());
        }

        return message;
    }
    @RequestMapping(value={"/getItemSelect"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public List<AttributeItem> getItemSelect(String attributeNo, @CurrentUser User user) { Sort sort = new Sort(Sort.Direction.ASC, new String[] { "t.field1" });
        List sl = this.attributeItemService.getItemSelect(user.getProjectCode(), sort, attributeNo);

        return sl;
    }

    protected void init(WebDataBinder arg0)
    {
    }
}