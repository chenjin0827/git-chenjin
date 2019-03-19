package com.chenjin.testPCQueue.sys.service.realm;

import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.NamedFilterList;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.stereotype.Service;

@Service("shiroChainDefinitionsManager")
public class ShiroChainDefinitionsManager
{

    @Resource(name="&shiroFilter")
    private ShiroFilterFactoryBean shiroFilterFactoryBean;
    public static final String PREMISSION_STRING = "authc, perms[\"{0}\"]";
    private Map<String, NamedFilterList> defaultFilterChains;

    public void initFilterChains(List<Map<String, String>> list)
    {
        AbstractShiroFilter shiroFilter = null;
        try {
            shiroFilter = (AbstractShiroFilter)this.shiroFilterFactoryBean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver)shiroFilter.getFilterChainResolver();
        DefaultFilterChainManager manager = (DefaultFilterChainManager)filterChainResolver.getFilterChainManager();

        this.defaultFilterChains = new LinkedHashMap(manager.getFilterChains());

        updateFilterChains(list);
    }

    public void updateFilterChains(List<Map<String, String>> list) {
        AbstractShiroFilter shiroFilter = null;
        try {
            shiroFilter = (AbstractShiroFilter)this.shiroFilterFactoryBean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver)shiroFilter.getFilterChainResolver();
        DefaultFilterChainManager manager = (DefaultFilterChainManager)filterChainResolver.getFilterChainManager();

        manager.getFilterChains().clear();
        this.shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
        if (this.defaultFilterChains != null) {
            manager.getFilterChains().putAll(this.defaultFilterChains);
        }
        if (list == null) {
            list = new ArrayList();
        }

        for (Map m : list)
        {
            if ((!StringUtils.isNotEmpty((CharSequence)m.get("PERMCODE"))) || (!StringUtils.isNotEmpty((CharSequence)m.get("URL")))) {
                continue;
            }
            manager.createChain((String)m.get("URL"), MessageFormat.format("authc, perms[\"{0}\"]", new Object[] { m.get("PERMCODE") }));
        }

        manager.createChain("/webService/**", "anon");
        manager.createChain("/resources/**", "anon");
        manager.createChain("/app/**", "anon");
        manager.createChain("/sys/login/**", "anon");

        manager.createChain("/**/*.htmlx", "authc");
        System.out.println("内存授权列表");
        System.out.println("list.size = " + list.size());

        System.out.println(manager.getFilterChains());
        System.out.println("内存授权列表   end");
    }
}