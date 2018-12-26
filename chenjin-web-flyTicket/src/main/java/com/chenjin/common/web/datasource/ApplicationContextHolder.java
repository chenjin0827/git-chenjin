package com.chenjin.common.web.datasource;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;

@Component("applicationContextHolder")
@Lazy(false)
public final class ApplicationContextHolder
        implements ApplicationContextAware, DisposableBean
{
    private static Logger logger = LoggerFactory.getLogger(ApplicationContextHolder.class);
    private static ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext)
    {
        applicationContext = applicationContext;
    }

    public void destroy() throws Exception {
        applicationContext = null;
    }

    public static String getRootRealPath() {
        String rootRealPath = "";
        try {
            rootRealPath = getApplicationContext().getResource("").getFile().getAbsolutePath();
        } catch (IOException e) {
            logger.warn("获取系统根目录失败");
        }
        return rootRealPath;
    }

    public static String getResourceRootRealPath() {
        String rootRealPath = "";
        try {
            rootRealPath = new DefaultResourceLoader().getResource("").getFile().getAbsolutePath();
        } catch (IOException e) {
            logger.warn("获取资源根目录失败");
        }
        return rootRealPath;
    }

    public static ApplicationContext getApplicationContext()
    {
        assertContextInjected();
        return applicationContext;
    }

    public static <T> T getBean(String name)
    {
        Assert.hasText(name);
        assertContextInjected();
        return (T)applicationContext.getBean(name);
    }

    public static <T> T getBean(String name, Class<T> type)
    {
        Assert.hasText(name);
        Assert.notNull(type);
        assertContextInjected();
        return applicationContext.getBean(name, type);
    }

    public static String getMessage(String code, Object[] args)
    {
        assertContextInjected();
        LocaleResolver localeResolver = (LocaleResolver)getBean("localeResolver", LocaleResolver.class);
        Locale locale = localeResolver.resolveLocale(null);
        return applicationContext.getMessage(code, args, locale);
    }

    private static void assertContextInjected()
    {
        Validate.validState(applicationContext != null, "applicaitonContext属性未注入, 请在applicationContext.xml中定义ApplicaitonContextHolder.", new Object[0]);
    }

    public static HttpServletRequest getRequest()
    {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    public static HttpSession getSession()
    {
        HttpSession session = getRequest().getSession();
        return session;
    }
}