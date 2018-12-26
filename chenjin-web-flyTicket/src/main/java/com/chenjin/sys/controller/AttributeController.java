package com.chenjin.sys.controller;

import com.chenjin.common.entity.DataGrid;
import com.chenjin.common.entity.PageRequest;
import com.chenjin.common.framework.annotation.CurrentUser;
import com.chenjin.common.web.controller.BaseController;
import com.chenjin.sys.dto.Message;
import com.chenjin.sys.entity.Attribute;
import com.chenjin.sys.entity.AttributeItem;
import com.chenjin.sys.entity.User;
import com.chenjin.sys.service.IAttributeItemService;
import com.chenjin.sys.service.IAttributeService;
import com.chenjin.sys.service.IUserService;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/sys/attribute"})
public class AttributeController extends BaseController
{

    @Resource(name="userService")
    private IUserService userService;

    @Resource(name="attributeService")
    private IAttributeService attributeService;

    @Resource(name="attributeItemService")
    private IAttributeItemService attributeItemService;

    @RequestMapping({""})
    public String home()
    {
        return "sys/attribute/list";
    }

    @RequestMapping({"/page"})
    @ResponseBody
    public DataGrid<Attribute> homePage(PageRequest pageable, @CurrentUser User user)
    {
        DataGrid page = this.attributeService.query(user.getProjectCode(), pageable);
        return page;
    }

    @RequestMapping(value={"/add"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public String add()
    {
        return "sys/attribute/add";
    }

    @RequestMapping(value={"/add"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public Message add(Attribute attribute, @CurrentUser User user)
    {
        Message message = new Message();
        try
        {
            Attribute attributeold = this.attributeService.queryByAttributeNo(user.getProjectCode(), null, attribute.getAttributeNo());
            if (attributeold == null) {
                this.attributeService.save(user.getProjectCode(), attribute);
                message.setMsg(attribute.getAttributeNo() + "添加成功");
            } else {
                message.setMsg("属性代号" + attribute.getAttributeNo() + "已经添加，不能重复添加");
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
        return "sys/attribute/edit";
    }

    @RequestMapping(value={"/edit"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public Message edit(Attribute attribute, @CurrentUser User user)
    {
        Message message = new Message();
        try {
            this.attributeService.updateWithInclude(user.getProjectCode(), attribute, new String[] { "attributeNo", "name", "description" });

            message.setMsg(attribute.getAttributeNo() + "修改成功");
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
        try {
            Attribute attribute = (Attribute)this.attributeService.getById(user.getProjectCode(), id);
            this.attributeService.delete(user.getProjectCode(), id);

            message.setMsg(attribute.getAttributeNo() + "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            message.setSuccess(false);
            message.setMsg(e.getMessage());
        }

        return message;
    }

    @RequestMapping({"/queryByAttributeItem"})
    @ResponseBody
    public DataGrid<AttributeItem> queryByAttributeItem(PageRequest pageablem, Long id, String searchkey, @CurrentUser User user)
    {
        Attribute attribute = new Attribute();
        if (id != null) {
            attribute.setId(id);
        }
        DataGrid page = this.attributeItemService.queryByAttributeItem(user.getProjectCode(), pageablem, attribute, searchkey);
        return page;
    }

    protected void init(WebDataBinder arg0)
    {
    }
}