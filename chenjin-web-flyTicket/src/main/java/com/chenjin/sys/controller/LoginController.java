package com.chenjin.sys.controller;

import com.chenjin.common.cache.dao.VerifyCodeDAO;
import com.chenjin.common.web.controller.BaseController;
import com.chenjin.common.web.util.VerifyCodeUtil;
import com.chenjin.commons.CommonProperties;
import com.chenjin.sys.dto.Message;
import com.chenjin.sys.entity.AttributeItem;
import com.chenjin.sys.entity.User;
import com.chenjin.sys.service.IAttributeItemService;
import com.chenjin.sys.service.IUserService;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/sys/login"})
public class LoginController extends BaseController
{

    @Resource
    private IUserService userService;

    @Resource
    private VerifyCodeDAO verifyCodeDAO;

    @Resource
    private IAttributeItemService attributeItemService;

    @RequestMapping(value={"/vcimage"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public void getVerifyCodeImage(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        System.out.println("in vcimage");

        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0L);
        String verifyCode = VerifyCodeUtil.generateTextCode(0, 4, null);

        this.verifyCodeDAO.save(request.getSession().getId(), verifyCode);

        System.out.println("本次生成的验证码为[" + verifyCode + "],已存放到缓存中");

        response.setContentType("image/jpeg");
        BufferedImage bufferedImage = VerifyCodeUtil.generateImageCode(verifyCode, 90, 30, 3, true, Color.WHITE, Color.BLACK, null);

        ImageIO.write(bufferedImage, "JPEG", response.getOutputStream());
    }

    @RequestMapping({"/projectCodeView"})
    public String projectCodeView(Model model)
            throws IOException
    {
        Map m = new HashMap();

        List l = new ArrayList();
        String projectCode = CommonProperties.DB_PROJECTCODE;
        String projectName = CommonProperties.DB_PROJECTNAME;
        String[] codes = projectCode.split(",");
        String[] names = projectName.split(",");
        for (int i = 0; i < codes.length; i++) {
            m = new HashMap();
            m.put("id", codes[i]);
            m.put("name", names[i]);
            l.add(m);
        }
        model.addAttribute("list", l);
        return "projectCodeList";
    }

    @RequestMapping({"/projectCode"})
    @ResponseBody
    public List<Map<String, String>> getProjectCode() throws IOException {
        Map m = new HashMap();

        List l = new ArrayList();
        String projectCode = CommonProperties.DB_PROJECTCODE;
        String projectName = CommonProperties.DB_PROJECTNAME;
        String[] codes = projectCode.split(",");
        String[] names = projectName.split(",");
        for (int i = 0; i < codes.length; i++) {
            m = new HashMap();
            m.put("id", codes[i]);
            m.put("name", names[i]);
            l.add(m);
        }
        return l;
    }

    @RequestMapping({"/info"})
    @ResponseBody
    public Map<String, String> info()
            throws IOException
    {
        Map m = new HashMap();

        AttributeItem a = this.attributeItemService.queryByAttrAndItemNo("", "LOGININFO", "LOGO");
        AttributeItem b = this.attributeItemService.queryByAttrAndItemNo("", "LOGININFO", "FOOTER");
        if (a != null)
            m.put("LOGO", a.getField3());
        if (b != null)
            m.put("FOOTER", b.getField3());
        return m;
    }

    @RequestMapping(value={"/updatePsw"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public String updatePsd(Model model)
    {
        model.addAttribute("checkpwd", Integer.valueOf(1));
        return "sys/user/updatePsw";
    }

    @RequestMapping({"/updatePsw"})
    @ResponseBody
    public Message updatePsd(String password, String empId, String newPsd)
    {
        Message message = new Message();
        try {
            System.out.println(empId); System.out.println(password); System.out.println(newPsd);
            User user = this.userService.findByEmpId("", empId);
            if ((user != null) && (password.equals(user.getPwd()))) {
                if (newPsd.equals("")) {
                    message.setSuccess(false);
                    message.setMsg("新密码不可为空！");
                } else {
                    user.setPwd(newPsd);
                    user.setPasswdDate(new Date());
                    this.userService.update("", user);
                    message.setSuccess(true);
                    message.setMsg("密码修改成功！");
                }
            } else {
                message.setSuccess(false);
                message.setMsg("密码修改失败");
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