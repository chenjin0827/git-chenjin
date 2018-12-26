package com.chenjin.common.framework.argumentsResolver;

import com.chenjin.common.framework.annotation.CurrentMember;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class CurrentMemberArgumentsResolver
        implements HandlerMethodArgumentResolver
{
    public boolean supportsParameter(MethodParameter parameter)
    {
        return parameter.hasParameterAnnotation(CurrentMember.class);
    }

    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory)
            throws Exception
    {
        return webRequest.getAttribute("currentMember", 1);
    }
}