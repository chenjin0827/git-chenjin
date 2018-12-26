package com.chenjin.sys.service.realm;

import com.chenjin.commons.CommonProperties;
import com.chenjin.sys.service.IResourceService;
import java.io.PrintStream;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class InitService
{

    @Resource
    private IResourceService resourceService;

    @Resource
    private ShiroChainDefinitionsManager shiroChainDefinitionsManager;

    @PostConstruct
    public void init()
    {
        List list = this.resourceService.getAllForShiro(CommonProperties.MAIN_PROJECTDS);
        System.out.println("list = " + list);
        this.shiroChainDefinitionsManager.initFilterChains(list);
    }
}