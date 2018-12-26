package com.chenjin.common.web.bind.annotation;

import com.chenjin.common.web.util.MapWapper;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestParameterPropertyValues;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.util.WebUtils;

public class FormModelMethodArgumentResolver extends BaseMethodArgumentResolver {
    private final Pattern INDEX_PATTERN = Pattern.compile("\\[(\\d+)\\]\\.?");
    private int autoGrowCollectionLimit = 2147483647;

    public FormModelMethodArgumentResolver() {
    }

    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(FormModel.class);
    }

    public final Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest request, WebDataBinderFactory binderFactory) throws Exception {
        String name = ((FormModel)parameter.getParameterAnnotation(FormModel.class)).value();
        Object target = mavContainer.containsAttribute(name) ? mavContainer.getModel().get(name) : this.createAttribute(name, parameter, binderFactory, request);
        WebDataBinder binder = binderFactory.createBinder(request, target, name);
        target = binder.getTarget();
        if (target != null) {
            this.bindRequestParameters(mavContainer, binderFactory, binder, request, parameter);
            this.validateIfApplicable(binder, parameter);
            if (binder.getBindingResult().hasErrors() && this.isBindExceptionRequired(binder, parameter)) {
                throw new BindException(binder.getBindingResult());
            }
        }

        target = binder.convertIfNecessary(binder.getTarget(), parameter.getParameterType());
        mavContainer.addAttribute(name, target);
        return target;
    }

    protected Object createAttribute(String attributeName, MethodParameter parameter, WebDataBinderFactory binderFactory, NativeWebRequest request) throws Exception {
        String value = this.getRequestValueForAttribute(attributeName, request);
        if (value != null) {
            Object attribute = this.createAttributeFromRequestValue(value, attributeName, parameter, binderFactory, request);
            if (attribute != null) {
                return attribute;
            }
        }

        Class<?> parameterType = parameter.getParameterType();
        if (!parameterType.isArray() && !List.class.isAssignableFrom(parameterType)) {
            if (Set.class.isAssignableFrom(parameterType)) {
                return HashSet.class.newInstance();
            } else {
                return MapWapper.class.isAssignableFrom(parameterType) ? MapWapper.class.newInstance() : BeanUtils.instantiateClass(parameter.getParameterType());
            }
        } else {
            return ArrayList.class.newInstance();
        }
    }

    protected String getRequestValueForAttribute(String attributeName, NativeWebRequest request) {
        Map<String, String> variables = this.getUriTemplateVariables(request);
        if (StringUtils.hasText((String)variables.get(attributeName))) {
            return (String)variables.get(attributeName);
        } else {
            return StringUtils.hasText(request.getParameter(attributeName)) ? request.getParameter(attributeName) : null;
        }
    }

    protected Object createAttributeFromRequestValue(String sourceValue, String attributeName, MethodParameter parameter, WebDataBinderFactory binderFactory, NativeWebRequest request) throws Exception {
        DataBinder binder = binderFactory.createBinder(request, (Object)null, attributeName);
        ConversionService conversionService = binder.getConversionService();
        if (conversionService != null) {
            TypeDescriptor source = TypeDescriptor.valueOf(String.class);
            TypeDescriptor target = new TypeDescriptor(parameter);
            if (conversionService.canConvert(source, target)) {
                return binder.convertIfNecessary(sourceValue, parameter.getParameterType(), parameter);
            }
        }

        return null;
    }

    protected void bindRequestParameters(ModelAndViewContainer mavContainer, WebDataBinderFactory binderFactory, WebDataBinder binder, NativeWebRequest request, MethodParameter parameter) throws Exception {
        Map<String, Boolean> hasProcessedPrefixMap = new HashMap();
        Class<?> targetType = binder.getTarget().getClass();
        ServletRequest servletRequest = this.prepareServletRequest(binder.getTarget(), request, parameter);
        WebDataBinder simpleBinder = binderFactory.createBinder(request, (Object)null, (String)null);
        Type type;
        Class componentType;
        if (Collection.class.isAssignableFrom(targetType)) {
            type = parameter.getGenericParameterType();
            componentType = Object.class;
            Collection target = (Collection)binder.getTarget();
            List targetList = new ArrayList(target);
            if (type instanceof ParameterizedType) {
                componentType = (Class)((ParameterizedType)type).getActualTypeArguments()[0];
            }

            if (parameter.getParameterType().isArray()) {
                componentType = parameter.getParameterType().getComponentType();
            }

            Iterator var14 = servletRequest.getParameterMap().keySet().iterator();

            while(true) {
                Object key;
                String prefixName;
                do {
                    if (!var14.hasNext()) {
                        return;
                    }

                    key = var14.next();
                    prefixName = this.getPrefixName((String)key);
                } while(hasProcessedPrefixMap.containsKey(prefixName));

                hasProcessedPrefixMap.put(prefixName, Boolean.TRUE);
                Matcher matcher;
                if (this.isSimpleComponent(prefixName)) {
                    Map<String, Object> paramValues = WebUtils.getParametersStartingWith(servletRequest, prefixName);
                    matcher = this.INDEX_PATTERN.matcher(prefixName);
                    if (!matcher.matches()) {
                        Iterator var32 = paramValues.values().iterator();

                        while(var32.hasNext()) {
                            Object value = var32.next();
                            targetList.add(simpleBinder.convertIfNecessary(value, componentType));
                        }
                    } else {
                        int index = Integer.valueOf(matcher.group(1)).intValue();
                        if (targetList.size() <= index) {
                            this.growCollectionIfNecessary(targetList, index);
                        }

                        targetList.set(index, simpleBinder.convertIfNecessary(paramValues.values(), componentType));
                    }
                } else {
                    Object component = null;
                    matcher = this.INDEX_PATTERN.matcher(prefixName);
                    if (!matcher.matches()) {
                        throw new IllegalArgumentException("bind collection error, need integer index, key:" + key);
                    }

                    if (component == null) {
                        component = BeanUtils.instantiate(componentType);
                    }

                    WebDataBinder componentBinder = binderFactory.createBinder(request, component, (String)null);
                    component = componentBinder.getTarget();
                    if (component != null) {
                        ServletRequestParameterPropertyValues pvs = new ServletRequestParameterPropertyValues(servletRequest, prefixName, "");
                        componentBinder.bind(pvs);
                        this.validateIfApplicable(componentBinder, parameter);
                        if (componentBinder.getBindingResult().hasErrors() && this.isBindExceptionRequired(componentBinder, parameter)) {
                            throw new BindException(componentBinder.getBindingResult());
                        }

                        targetList.add(component);
                    }
                }

                target.clear();
                target.addAll(targetList);
            }
        } else if (MapWapper.class.isAssignableFrom(targetType)) {
            type = parameter.getGenericParameterType();
            componentType = Object.class;
            Class<?> valueType = Object.class;
            if (type instanceof ParameterizedType) {
                componentType = (Class)((ParameterizedType)type).getActualTypeArguments()[0];
                valueType = (Class)((ParameterizedType)type).getActualTypeArguments()[1];
            }

            MapWapper mapWapper = (MapWapper)binder.getTarget();
            Map target = mapWapper.getInnerMap();
            if (target == null) {
                target = new HashMap();
                mapWapper.setInnerMap((Map)target);
            }

            Iterator var26 = servletRequest.getParameterMap().keySet().iterator();

            while(true) {
                while(true) {
                    String prefixName;
                    do {
                        if (!var26.hasNext()) {
                            return;
                        }

                        Object key = var26.next();
                        prefixName = this.getPrefixName((String)key);
                    } while(hasProcessedPrefixMap.containsKey(prefixName));

                    hasProcessedPrefixMap.put(prefixName, Boolean.TRUE);
                    Object keyValue = simpleBinder.convertIfNecessary(this.getMapKey(prefixName), componentType);
                    if (this.isSimpleComponent(prefixName)) {
                        Map<String, Object> paramValues = WebUtils.getParametersStartingWith(servletRequest, prefixName);
                        Iterator var36 = paramValues.values().iterator();

                        while(var36.hasNext()) {
                            Object value = var36.next();
                            ((Map)target).put(keyValue, simpleBinder.convertIfNecessary(value, valueType));
                        }
                    } else {
                        Object component = ((Map)target).get(keyValue);
                        if (component == null) {
                            component = BeanUtils.instantiate(valueType);
                        }

                        WebDataBinder componentBinder = binderFactory.createBinder(request, component, (String)null);
                        component = componentBinder.getTarget();
                        if (component != null) {
                            ServletRequestParameterPropertyValues pvs = new ServletRequestParameterPropertyValues(servletRequest, prefixName, "");
                            componentBinder.bind(pvs);
                            this.validateComponent(componentBinder, parameter);
                            ((Map)target).put(keyValue, component);
                        }
                    }
                }
            }
        } else {
            ServletRequestDataBinder servletBinder = (ServletRequestDataBinder)binder;
            servletBinder.bind(servletRequest);
        }
    }

    private void growCollectionIfNecessary(Collection collection, int index) {
        if (index >= collection.size() && index < this.autoGrowCollectionLimit) {
            for(int i = collection.size(); i <= index; ++i) {
                collection.add((Object)null);
            }
        }

    }

    private Object getMapKey(String prefixName) {
        String key = prefixName;
        if (prefixName.startsWith("['")) {
            key = prefixName.replaceAll("\\['", "").replaceAll("'\\]", "");
        }

        if (key.startsWith("[\"")) {
            key = key.replaceAll("\\[\"", "").replaceAll("\"\\]", "");
        }

        if (key.startsWith("[")) {
            key = key.replaceAll("\\[", "").replaceAll("\\]", "");
        }

        if (key.endsWith(".")) {
            key = key.substring(0, key.length() - 1);
        }

        return key;
    }

    private boolean isSimpleComponent(String prefixName) {
        return !prefixName.endsWith(".");
    }

    private String getPrefixName(String name) {
        int begin = 0;
        int end = name.indexOf("]") + 1;
        if (name.indexOf("].") >= 0) {
            ++end;
        }

        return name.substring(begin, end);
    }

    private ServletRequest prepareServletRequest(Object target, NativeWebRequest request, MethodParameter parameter) {
        FormModel formModel = (FormModel)parameter.getParameterAnnotation(FormModel.class);
        String modelPrefixName = formModel.value();
        String ckbox = formModel.checkbox();
        HttpServletRequest nativeRequest = (HttpServletRequest)request.getNativeRequest();
        MultipartRequest multipartRequest = (MultipartRequest)WebUtils.getNativeRequest(nativeRequest, MultipartRequest.class);
        MockHttpServletRequest mockRequest = null;
        Iterator var11;
        if (multipartRequest != null) {
            MockMultipartHttpServletRequest mockMultipartRequest = new MockMultipartHttpServletRequest();
            var11 = multipartRequest.getFileMap().values().iterator();

            while(var11.hasNext()) {
                MultipartFile file = (MultipartFile)var11.next();
                mockMultipartRequest.addFile(new FormModelMethodArgumentResolver.MultipartFileWrapper(this.getNewParameterName(file.getName(), modelPrefixName), file));
            }

            mockRequest = mockMultipartRequest;
        } else {
            mockRequest = new MockHttpServletRequest();
        }

        Iterator var16 = this.getUriTemplateVariables(request).entrySet().iterator();

        while(var16.hasNext()) {
            Entry<String, String> entry = (Entry)var16.next();
            String parameterName = (String)entry.getKey();
            String value = (String)entry.getValue();
            if (this.isFormModelAttribute(parameterName, modelPrefixName)) {
                ((MockHttpServletRequest)mockRequest).setParameter(this.getNewParameterName(parameterName, modelPrefixName), value);
            }
        }

        String[] checkbox = null;
        if (!StringUtils.isEmpty(ckbox)) {
            checkbox = nativeRequest.getParameterValues(ckbox);
        }

        var11 = nativeRequest.getParameterMap().entrySet().iterator();

        while(var11.hasNext()) {
            Object parameterEntry = var11.next();
            Entry<String, String[]> entry = (Entry)parameterEntry;
            String parameterName = (String)entry.getKey();
            String[] value = (String[])entry.getValue();
            if (this.isFormModelAttribute(parameterName, modelPrefixName) && this.loopCheck(ckbox, checkbox, parameterName, modelPrefixName)) {
                ((MockHttpServletRequest)mockRequest).setParameter(this.getNewParameterName(parameterName, modelPrefixName), value);
            }
        }

        return (ServletRequest)mockRequest;
    }

    private boolean loopCheck(String checkbox, String[] values, String parameterName, String modelPrefixName) {
        if (StringUtils.isEmpty(checkbox)) {
            return true;
        } else if (values == null) {
            return false;
        } else {
            parameterName = this.getNewParameterName(parameterName, modelPrefixName);
            String prefixName = this.getPrefixName(parameterName);
            Matcher matcher = this.INDEX_PATTERN.matcher(prefixName);
            if (!matcher.matches()) {
                return false;
            } else {
                String index = matcher.group(1);
                String[] var8 = values;
                int var9 = values.length;

                for(int var10 = 0; var10 < var9; ++var10) {
                    String str = var8[var10];
                    if (str.equals(index)) {
                        return true;
                    }
                }

                return false;
            }
        }
    }

    private String getNewParameterName(String parameterName, String modelPrefixName) {
        int modelPrefixNameLength = modelPrefixName.length();
        if (parameterName.charAt(modelPrefixNameLength) == '.') {
            return parameterName.substring(modelPrefixNameLength + 1);
        } else if (parameterName.charAt(modelPrefixNameLength) == '[') {
            return parameterName.substring(modelPrefixNameLength);
        } else {
            throw new IllegalArgumentException("illegal request parameter, can not binding to @FormBean(" + modelPrefixName + ")");
        }
    }

    private boolean isFormModelAttribute(String parameterName, String modelPrefixName) {
        int modelPrefixNameLength = modelPrefixName.length();
        if (parameterName.length() == modelPrefixNameLength) {
            return false;
        } else if (!parameterName.startsWith(modelPrefixName)) {
            return false;
        } else {
            char ch = parameterName.charAt(modelPrefixNameLength);
            return ch == '.' || ch == '[';
        }
    }

    protected void validateComponent(WebDataBinder binder, MethodParameter parameter) throws BindException {
        boolean validateParameter = this.validateParameter(parameter);
        Annotation[] annotations = binder.getTarget().getClass().getAnnotations();
        Annotation[] var5 = annotations;
        int var6 = annotations.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            Annotation annot = var5[var7];
            if (annot.annotationType().getSimpleName().startsWith("Valid") && validateParameter) {
                Object hints = AnnotationUtils.getValue(annot);
                binder.validate(hints instanceof Object[] ? (Object[])((Object[])hints) : new Object[]{hints});
            }
        }

        if (binder.getBindingResult().hasErrors() && this.isBindExceptionRequired(binder, parameter)) {
            throw new BindException(binder.getBindingResult());
        }
    }

    private boolean validateParameter(MethodParameter parameter) {
        Annotation[] annotations = parameter.getParameterAnnotations();
        Annotation[] var3 = annotations;
        int var4 = annotations.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Annotation annot = var3[var5];
            if (annot.annotationType().getSimpleName().startsWith("Valid")) {
                return true;
            }
        }

        return false;
    }

    protected void validateIfApplicable(WebDataBinder binder, MethodParameter parameter) {
        Annotation[] annotations = parameter.getParameterAnnotations();
        Annotation[] var4 = annotations;
        int var5 = annotations.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Annotation annot = var4[var6];
            if (annot.annotationType().getSimpleName().startsWith("Valid")) {
                Object hints = AnnotationUtils.getValue(annot);
                binder.validate(hints instanceof Object[] ? (Object[])((Object[])hints) : new Object[]{hints});
            }
        }

    }

    protected boolean isBindExceptionRequired(WebDataBinder binder, MethodParameter parameter) {
        int i = parameter.getParameterIndex();
        Class<?>[] paramTypes = parameter.getMethod().getParameterTypes();
        boolean hasBindingResult = paramTypes.length > i + 1 && Errors.class.isAssignableFrom(paramTypes[i + 1]);
        return !hasBindingResult;
    }

    private static class MultipartFileWrapper implements MultipartFile {
        private String name;
        private MultipartFile delegate;

        private MultipartFileWrapper(String name, MultipartFile delegate) {
            this.name = name;
            this.delegate = delegate;
        }

        public String getName() {
            return this.name;
        }

        public String getOriginalFilename() {
            return this.delegate.getOriginalFilename();
        }

        public String getContentType() {
            return this.delegate.getContentType();
        }

        public boolean isEmpty() {
            return this.delegate.isEmpty();
        }

        public long getSize() {
            return this.delegate.getSize();
        }

        public byte[] getBytes() throws IOException {
            return this.delegate.getBytes();
        }

        public InputStream getInputStream() throws IOException {
            return this.delegate.getInputStream();
        }

        public void transferTo(File dest) throws IOException, IllegalStateException {
            this.delegate.transferTo(dest);
        }
    }
}
