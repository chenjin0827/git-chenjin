package com.chenjin.sys.controller;

import com.chenjin.common.entity.DataGrid;
import com.chenjin.common.entity.PageRequest;
import com.chenjin.common.web.controller.BaseController;
import com.chenjin.sys.entity.User;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/sys/userList"})
public class UserlistController extends BaseController
{

    @Autowired
    private SessionDAO sessionDAO;

    @RequestMapping({""})
    public String home()
    {
        return "sys/userList/list";
    }

    @RequestMapping({"/page"})
    @ResponseBody
    public DataGrid<User> groupPage(PageRequest pageable)
    {
        List list = new ArrayList();
        Collection<Session> sessions = this.sessionDAO.getActiveSessions();
        System.out.println("session.size = " + sessions.size());
        for (Session session : sessions) {
            User user = (User)session.getAttribute("currentUser");

            if (user != null) {
                list.add(user);
            }

        }

        DataGrid page = new DataGrid(list, pageable, list.size());
        return page;
    }

    protected void init(WebDataBinder binder)
    {
    }
}