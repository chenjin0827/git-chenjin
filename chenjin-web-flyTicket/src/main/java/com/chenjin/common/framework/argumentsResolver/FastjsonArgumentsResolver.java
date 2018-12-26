
package com.chenjin.common.framework.argumentsResolver;

import com.alibaba.fastjson.JSON;
import com.chenjin.common.framework.annotation.Fastjson;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class FastjsonArgumentsResolver implements HandlerMethodArgumentResolver {
    public FastjsonArgumentsResolver() {
    }

    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Fastjson.class);
    }

    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        System.out.println("fastjson in");
        HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest(HttpServletRequest.class);
        String fjs = request.getParameter(parameter.getParameterName());
        if (fjs == null) {
            return null;
        } else {
            System.out.println("fastjson=" + fjs);
            Class<?> fieldClazz = parameter.getParameterType();
            if (fieldClazz.isAssignableFrom(List.class)) {
                System.out.println("json->list");
                Type fc = parameter.getGenericParameterType();
                if (fc != null && fc instanceof ParameterizedType) {
                    ParameterizedType pt = (ParameterizedType)fc;
                    Class<?> genericClazz = (Class)pt.getActualTypeArguments()[0];
                    return JSON.parseArray(fjs, genericClazz);
                } else {
                    return null;
                }
            } else if (fieldClazz.isAssignableFrom(Map.class)) {
                System.out.println("json->map");
                return JSON.parseObject(fjs, Map.class);
            } else {
                System.out.println("json->other");
                return JSON.parseObject(fjs, parameter.getParameterType());
            }
        }
    }
}
