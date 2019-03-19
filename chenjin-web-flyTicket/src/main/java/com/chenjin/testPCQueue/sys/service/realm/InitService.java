package com.chenjin.testPCQueue.sys.service.realm;

import com.chenjin.testPCQueue.commons.CommonProperties;
import com.chenjin.testPCQueue.sys.service.IResourceService;

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