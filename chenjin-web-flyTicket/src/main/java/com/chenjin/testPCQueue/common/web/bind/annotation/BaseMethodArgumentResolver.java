package com.chenjin.testPCQueue.common.web.bind.annotation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerMapping;

public abstract class BaseMethodArgumentResolver
        implements HandlerMethodArgumentResolver
{
    protected Map<String, String[]> getPrefixParameterMap(String namePrefix, NativeWebRequest request, boolean subPrefix)
    {
        Map result = new HashMap();

        Map<String,String> variables = (Map<String,String>)getUriTemplateVariables(request);

        int namePrefixLength = namePrefix.length();
        for (String name : variables.keySet()) {
            if (name.startsWith(namePrefix))
            {
                if (subPrefix) {
                    char ch = name.charAt(namePrefixLength);

                    if (illegalChar(ch)) {
                        continue;
                    }
                    result.put(name.substring(namePrefixLength + 1), new String[] { (String)variables.get(name) });
                } else {
                    result.put(name, new String[] { (String)variables.get(name) });
                }
            }
        }

        Iterator parameterNames = request.getParameterNames();
        while (parameterNames.hasNext()) {
            String name = (String)parameterNames.next();
            if (name.startsWith(namePrefix))
            {
                if (subPrefix) {
                    char ch = name.charAt(namePrefixLength);

                    if (illegalChar(ch)) {
                        continue;
                    }
                    result.put(name.substring(namePrefixLength + 1), request.getParameterValues(name));
                } else {
                    result.put(name, request.getParameterValues(name));
                }
            }
        }

        return result;
    }

    protected Map<String, String[]> getSearchPropertyParameterMap(String namePrefix, NativeWebRequest request)
    {
        Map result = new HashMap();

        Map<String,String> variables = (Map<String,String>)getUriTemplateVariables(request);

        int namePrefixLength = namePrefix.length();
        for (String name : variables.keySet()) {
            if (name.startsWith(namePrefix)) {
                char ch = name.charAt(namePrefixLength);

                if (illegalChar(ch)) {
                    continue;
                }
                String _name = name.substring(namePrefixLength + 1);
                String _value = (String)variables.get(name);
                if (StringUtils.isNotEmpty(_value)) {
                    result.put(_value, new String[] { (String)variables.get(_name) });
                }
            }
        }

        Iterator parameterNames = request.getParameterNames();
        while (parameterNames.hasNext()) {
            String name = (String)parameterNames.next();
            if (name.startsWith(namePrefix)) {
                char ch = name.charAt(namePrefixLength);

                if (illegalChar(ch)) {
                    continue;
                }
                String _name = name.substring(namePrefixLength + 1);
                String _value = request.getParameter(name);
                if (StringUtils.isNotEmpty(_value)) {
                    result.put(_value, request.getParameterValues(_name));
                }
            }
        }

        return result;
    }

    private boolean illegalChar(char ch) {
        return (ch != '.') && (ch != '_') && ((ch < '0') || (ch > '9'));
    }

    protected final Map<String, String> getUriTemplateVariables(NativeWebRequest request)
    {
        Map variables = (Map)request
                .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, 0);

        return variables != null ? variables : Collections.emptyMap();
    }
}