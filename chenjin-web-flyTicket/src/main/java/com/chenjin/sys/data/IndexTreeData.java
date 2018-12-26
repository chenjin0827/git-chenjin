package com.chenjin.sys.data;

import com.chenjin.common.framework.annotation.CurrentUser;
import com.chenjin.sys.entity.User;
import com.chenjin.sys.service.IGroupUserService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/indexTree"})
public class IndexTreeData
{

    @Resource
    private IGroupUserService groupUserService;

    @RequestMapping({""})
    @ResponseBody
    public List<Map<String, Object>> home(@CurrentUser User currUser)
    {
        List list = this.groupUserService.getIndexTree(currUser.getProjectCode(), currUser.getId());
        return list;
    }
}