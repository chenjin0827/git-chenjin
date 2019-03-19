package com.chenjin.testPCQueue.common.framework.util;

import com.chenjin.testPCQueue.common.entity.BaseEntity;
import com.chenjin.testPCQueue.common.entity.BasicEntity;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Embedded;
import javax.persistence.Transient;

public class EntityUtil
{
    private Class<?> entityClass;

    public EntityUtil(Class<?> c)
    {
        this.entityClass = c;
    }

    public List<String> getKeyList()
            throws Exception
    {
        List keyList = new ArrayList();

        Method[] ms = this.entityClass.getMethods();
        for (Method method : ms) {
            setKeyList(this.entityClass, method, keyList);
        }

        return keyList;
    }

    public List<Object> getValList(Object obj)
            throws Exception
    {
        List valList = new ArrayList();

        Method[] ms = this.entityClass.getMethods();
        for (Method method : ms) {
            setValList(this.entityClass, method, obj, valList);
        }

        return valList;
    }

    private void setValList(Class<?> c, Method method, Object t, List<Object> valList) throws Exception {
        String name = method.getName();
        if (isTransient(c, name)) {
            return;
        }
        if (isEmbedded(c, name)) {
            addEmbeddedVal(c, name, t, valList);
            return;
        }
        addFinalValue(c, t, method, valList);
    }

    private void setKeyList(Class<?> c, Method method, List<String> keyList) throws Exception {
        String name = method.getName();
        if (isTransient(c, name)) {
            return;
        }

        if (isEmbedded(c, name)) {
            addEmbeddedKey(c, name, keyList);
            return;
        }
        addFinalKey(c, name, method, keyList);
    }

    private void addEmbeddedVal(Class<?> c, String methodName, Object t, List<Object> valList)
            throws Exception
    {
        Method getidm = c.getMethod(methodName, new Class[0]);
        Embedded e = (Embedded)getidm.getAnnotation(Embedded.class);
        if (e != null) {
            Object et = getidm.invoke(t, new Object[0]);
            Class ec = getidm.getReturnType();
            Method[] ms = ec.getMethods();
            for (Method method : ms)
                addFinalValue(ec, et, method, valList);
        }
    }

    private void addFinalValue(Class<?> c, Object t, Method method, List<Object> valList)
            throws Exception
    {
        String methodName = method.getName();
        if (!isTransient(c, methodName)) {
            Object value = method.invoke(t, new Object[0]);
            Class type = method.getReturnType();
            if (isBasicEntity(type)) {
                Method subm = type.getMethod("getId", new Class[0]);
                value = (Long)subm.invoke(value, new Object[0]);
            } else if (isEnum(type)) {
                Method subm = type.getMethod("ordinal", new Class[0]);
                value = (Integer)subm.invoke(value, new Object[0]);
            }
            valList.add(value);
        }
    }

    private void addFinalKey(Class<?> c, Object t, Method method, List<String> keyList) throws Exception {
        Class type = method.getReturnType();
        String name = method.getName();
        if (isBasicEntity(type))
            keyList.add(name.substring(3) + "Id");
        else
            keyList.add(name.substring(3));
    }

    private void addEmbeddedKey(Class<?> c, String methodName, List<String> keyList) throws Exception {
        Method getidm = c.getMethod(methodName, new Class[0]);
        Embedded e = (Embedded)getidm.getAnnotation(Embedded.class);
        if (e != null) {
            Class ec = getidm.getReturnType();
            Method[] ms = ec.getMethods();
            for (Method method : ms) {
                Class type = method.getReturnType();
                String name = method.getName();
                if (isTransient(ec, name)) {
                    continue;
                }
                if (isBasicEntity(type))
                    keyList.add(name.substring(3) + "Id");
                else
                    keyList.add(name.substring(3));
            }
        }
    }

    private boolean isEmbedded(Class<?> c, String name) throws Exception
    {
        return c.getMethod(name, new Class[0]).getAnnotation(Embedded.class) != null;
    }

    private boolean isTransient(Class<?> c, String name)
            throws Exception
    {
        if (name.equalsIgnoreCase("getClass")) {
            return true;
        }
        if (name.equalsIgnoreCase("getId")) {
            return true;
        }
        if (!name.startsWith("get")) {
            return true;
        }

        Method getidm = c.getMethod(name, new Class[0]);
        for (Annotation an : getidm.getAnnotations()) {
            if ((an instanceof Transient)) {
                return true;
            }
        }
        return false;
    }

    private boolean isBasicEntity(Class<?> type)
    {
        if (BasicEntity.class.isAssignableFrom(type)) {
            return true;
        }

        return BaseEntity.class.isAssignableFrom(type);
    }

    private boolean isEnum(Class<?> type)
    {
        return Enum.class.isAssignableFrom(type);
    }
}