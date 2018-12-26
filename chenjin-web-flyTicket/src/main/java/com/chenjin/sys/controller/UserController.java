package com.chenjin.sys.controller;

import com.chenjin.common.entity.DataGrid;
import com.chenjin.common.entity.PageRequest;
import com.chenjin.common.framework.annotation.CurrentUser;
import com.chenjin.common.web.controller.BaseController;
import com.chenjin.sys.dto.Message;
import com.chenjin.sys.entity.AttributeItem;
import com.chenjin.sys.entity.Organization;
import com.chenjin.sys.entity.User;
import com.chenjin.sys.service.IAttributeItemService;
import com.chenjin.sys.service.IOrganizationService;
import com.chenjin.sys.service.IUserService;
import java.io.PrintStream;
import java.util.Date;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping({"/sys/user"})
public class UserController extends BaseController
{

    @Resource
    private IUserService userService;

    @Resource
    private IOrganizationService organizationService;

    @Resource
    private IAttributeItemService attributeItemService;
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping({""})
    public String home()
    {
        return "sys/user/list";
    }

    @RequestMapping({"/page"})
    @ResponseBody
    public DataGrid<User> homePage(PageRequest pageable, @CurrentUser User user)
    {
        Map m = pageable.getQuery();
        m.put("t#empId_S_NE", "admin");
        int orgType = user.getOrganization().getOrgType().intValue();
        if ((orgType != 5) && (orgType != 9)) {
            m.put("t#organization.id_L_EQ", user.getOrganization().getId());
        }
        DataGrid page = this.userService.query(user.getProjectCode(), pageable);

        return page;
    }

    @RequestMapping(value={"/add"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public String add()
    {
        return "sys/user/add";
    }

    @RequestMapping(value={"/add"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public Message add(String name, User user, @CurrentUser User currentuser)
    {
        System.out.println("name=" + name);
        System.out.println("user.name=" + user.getName());
        Message message = new Message();
        try
        {
            User userold = this.userService.findByEmpId(user.getProjectCode(), user.getEmpId());
            if (userold != null) {
                message.setSuccess(false);
                message.setMsg("工号" + user.getEmpId() + "已存在，无法添加！");
            } else {
                String md5pwd = DigestUtils.md5Hex(user.getEmpId());
                user.setPwd(md5pwd);
                int orgtype = currentuser.getOrganization().getOrgType().intValue();
                if ((orgtype == 5) || (orgtype == 9)) {
                    Organization o = (Organization)this.organizationService.getById(currentuser.getProjectCode(), user.getOrganization().getId());
                    user.setOrganization(o);
                } else {
                    user.setOrganization(currentuser.getOrganization());
                    user.setIsAdmin("0");
                }
                user.setProjectCode(currentuser.getProjectCode());
                user.setPasswdDate(new Date());
                this.userService.save(currentuser.getProjectCode(), user);
                message.setSuccess(true);
                message.setMsg("用户" + user.getName() + "添加成功");
                logger.info(currentuser.getEmpId() + "添加了新用户[" + user.getEmpId() + "]");
            }
        } catch (Exception e) {
            e.printStackTrace();
            message.setSuccess(false);
            message.setMsg(e.getMessage());
        }

        return message;
    }

    @RequestMapping(value={"/edit"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public String edit(Long orgType, Long orgId, Model model)
    {
        if (orgType != null) {
            model.addAttribute("combox1", orgType);
        }
        if (orgId != null)
        {
            model.addAttribute("combox2", orgId);
        }
        return "sys/user/edit";
    }

    @RequestMapping(value={"/edit"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public Message edit(User user, @CurrentUser User currentuser)
    {
        Message message = new Message();
        try {
            int orgtype = currentuser.getOrganization().getOrgType().intValue();
            if ((orgtype == 5) || (orgtype == 9)) {
                Organization o = (Organization)this.organizationService.getById(currentuser.getProjectCode(), user.getOrganization().getId());
                user.setOrganization(o);
                this.userService.updateWithInclude(currentuser.getProjectCode(), user, new String[] { "empId", "mail", "name", "title", "cell", "ext", "organization", "clientCert", "idcard", "isAdmin" });
            }
            else {
                user.setOrganization(currentuser.getOrganization());
                this.userService.updateWithInclude(currentuser.getProjectCode(), user, new String[] { "empId", "mail", "name", "title", "cell", "ext", "organization", "clientCert", "idcard" });
            }

            message.setSuccess(true);
            logger.info(currentuser.getEmpId() + "修改用户资料[" + user.getEmpId() + "]");
        } catch (Exception e) {
            e.printStackTrace();
            message.setSuccess(false);
        }

        return message;
    }

    @RequestMapping(value={"/del"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public Message del(Long id, @CurrentUser User currentuser)
    {
        Message message = new Message();
        try
        {
            User user = (User)this.userService.getById(currentuser.getProjectCode(), id);
            this.userService.delete(currentuser.getProjectCode(), id);
            message.setSuccess(true);
            logger.info(currentuser.getEmpId() + "删除了用户[" + user.getEmpId() + "]");
        } catch (Exception e) {
            e.printStackTrace();
            message.setSuccess(false);
        }

        return message;
    }

    @RequestMapping(value={"/unlock"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public Message unlock(User user, @CurrentUser User currUser)
    {
        Message message = new Message();
        try
        {
            user.setIsLocked(Integer.valueOf(0));
            user.setErrTimes(Integer.valueOf(0));
            this.userService.updateWithInclude(currUser.getProjectCode(), user, new String[] { "isLocked", "errTimes" });
            message.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            message.setSuccess(false);
        }

        return message;
    }

    @RequestMapping(value={"/disabled"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public Message disabledUser(User user, @CurrentUser User currentuser)
    {
        Message message = new Message();
        try
        {
            user.setIsDisabled(Integer.valueOf(1));
            this.userService.updateWithInclude(currentuser.getProjectCode(), user, new String[] { "isDisabled" });
            message.setSuccess(true);
            User u = (User)this.userService.getById(currentuser.getProjectCode(), user.getId());
            logger.info(currentuser.getEmpId() + "禁用了用户[" + u.getEmpId() + "]");
        } catch (Exception e) {
            e.printStackTrace();
            message.setSuccess(false);
        }

        return message;
    }

    @RequestMapping(value={"/abled"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public Message abledUser(User user, @CurrentUser User currUser)
    {
        Message message = new Message();
        try
        {
            user.setIsDisabled(Integer.valueOf(0));
            this.userService.updateWithInclude(currUser.getProjectCode(), user, new String[] { "isDisabled" });
            message.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            message.setSuccess(false);
        }

        return message;
    }

    @RequestMapping(value={"/resetPwd"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public Message resetPwd(User user, @CurrentUser User currUser)
    {
        Message message = new Message();
        try
        {
            user.setPwd(user.getEmpId());
            user.setPasswdDate(new Date());
            this.userService.updateWithInclude(currUser.getProjectCode(), user, new String[] { "pwd", "passwdDate" });
            message.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            message.setSuccess(false);
        }

        return message;
    }

    @RequestMapping(value={"/registerSSO"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public Message registerSSO(Long userId, String idcard, @CurrentUser User currentUser)
    {
        Message message = new Message();
        try {
            System.out.println("userId:" + userId);
            System.out.println("idcard:" + idcard);
            User user = (User)this.userService.getById(userId);
            if (user == null) {
                throw new Exception("用户不存在");
            }
            user.setIdcard(idcard);
            this.userService.update(user);
            System.out.println("单点登录注册" + user);

            this.userService.registerSSO(user, currentUser);
        } catch (Exception e) {
            e.printStackTrace();
            message.setSuccess(false);
        }

        return message;
    }

    @RequestMapping(value={"/firstPwd"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public String firstPwd()
    {
        return "sys/user/first";
    }

    @RequestMapping(value={"/firstPwd"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    public String firstPwd(String pwd, @CurrentUser User user)
    {
        try
        {
            System.out.println("user:" + user);
            user.setPwd(pwd);
            user.setPasswdDate(new Date());
            this.userService.updateWithInclude(user.getProjectCode(), user, new String[] { "pwd", "passwdDate" });
            setSession("needChgPwd", Integer.valueOf(0));
            return "redirect:/index.jsp";
        } catch (Exception e) {
            e.printStackTrace();
        }return "redirect:/home.jsp";
    }

    private void setSession(Object key, Object value)
    {
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser != null) {
            Session session = currentUser.getSession();
            if (session != null)
                session.setAttribute(key, value);
        }
    }

    @RequestMapping(value={"/upload"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public Message upload(RedirectAttributes redirectAttributes, HttpServletRequest request, MultipartFile myfile)
    {
        MultipartHttpServletRequest mulRequest = (MultipartHttpServletRequest)request;
        Message message = new Message();
        MultipartFile file = mulRequest.getFile(myfile.getName());
        String filename = file.getOriginalFilename();
        if ((filename == null) || ("".equals(filename)))
        {
            message.setSuccess(false);
            message.setMsg("文件不能为空");
            return message;
        }
        try {
            if (filename.endsWith(".xls"))
            {
                message.setSuccess(true);
                String msg = this.userService.doExcelH(file);
                message.setMsg(msg);
            }
            else {
                message.setSuccess(false);
                message.setMsg("请用正确模版格式导入");
            }
        } catch (Exception e) {
            e.printStackTrace();
            message.setSuccess(false);
            message.setMsg("请用正确模版格式导入");
        }
        return message;
    }

    @RequestMapping(value={"/updatePsw"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public String updatePsd(@CurrentUser User currUser, Model model)
    {
        AttributeItem attributeItem = this.attributeItemService.queryByAttrAndItemNo(currUser.getProjectCode(), "publicUser", "CHECKPWD");
        if ((attributeItem != null) && (attributeItem.getField3().equals("1"))) {
            model.addAttribute("checkpwd", Integer.valueOf(1));
        }
        return "sys/user/updatePsw";
    }

    @RequestMapping({"/updatePsw"})
    @ResponseBody
    public Message updatePsd(HttpServletRequest requset, @CurrentUser User currUser)
    {
        String psd = requset.getParameter("password") == null ? "" : requset.getParameter("password");
        Long id = Long.valueOf(Integer.parseInt(requset.getParameter("id")));
        String NewPsd = requset.getParameter("NewPsd") == null ? "" : requset.getParameter("NewPsd");
        String NewPsd1 = requset.getParameter("NewPsd1") == null ? "" : requset.getParameter("NewPsd1");
        Message message = new Message();
        try {
            DigestUtils.md5Hex(psd);
            User user = (User)this.userService.getById(currUser.getProjectCode(), id);
            if (psd.equals(user.getPwd())) {
                if (NewPsd.equals(NewPsd1)) {
                    if (NewPsd.equals("")) {
                        message.setSuccess(false);
                        message.setMsg("新密码不可为空！");
                    } else {
                        String password = DigestUtils.md5Hex(NewPsd);
                        user.setPwd(password);
                        user.setPasswdDate(new Date());
                        this.userService.update(currUser.getProjectCode(), user);
                        message.setSuccess(true);
                        message.setMsg("密码修改成功！");
                    }
                } else {
                    message.setSuccess(false);
                    message.setMsg("密码修改失败,两次密码不一致！");
                }
            }
            else
            {
                message.setSuccess(false);
                message.setMsg("原密码错误,请重试！");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            message.setSuccess(false);
            message.setMsg("密码修改失败,系统错误！" + e.getMessage());
        }

        return message;
    }

    protected void init(WebDataBinder binder)
    {
    }
}