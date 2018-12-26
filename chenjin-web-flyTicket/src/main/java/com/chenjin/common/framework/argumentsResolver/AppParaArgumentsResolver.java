package com.chenjin.common.framework.argumentsResolver;

import com.alibaba.fastjson.JSON;
import com.chenjin.common.framework.annotation.AppPara;
import java.math.BigDecimal;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AppParaArgumentsResolver
        implements HandlerMethodArgumentResolver
{
    public boolean supportsParameter(MethodParameter parameter)
    {
        return parameter.hasParameterAnnotation(AppPara.class);
    }

    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory)
            throws Exception
    {
        HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest(HttpServletRequest.class);
        String parameterName = parameter.getParameterName();
        String fjs = request.getParameter(((AppPara)parameter.getParameterAnnotation(AppPara.class)).value());
        if (StringUtils.isBlank(fjs)) {
            return null;
        }

        Map bodyMap = (Map)JSON.parseObject(fjs, Map.class);
        Object obj = bodyMap.get(parameterName);
        if (obj == null) {
            return null;
        }
        Class fieldClazz = parameter.getParameterType();
        if (fieldClazz == String.class)
            return obj.toString();
        if (fieldClazz == Long.class)
            return Long.valueOf(Long.parseLong(obj.toString()));
        if (fieldClazz == Integer.class)
            return Integer.valueOf(Integer.parseInt(obj.toString()));
        if (fieldClazz == BigDecimal.class) {
            return new BigDecimal(obj.toString());
        }
        return obj;
    }
}