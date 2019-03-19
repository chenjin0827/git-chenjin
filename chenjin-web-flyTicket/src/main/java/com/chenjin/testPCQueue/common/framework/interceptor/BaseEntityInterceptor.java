package com.chenjin.testPCQueue.common.framework.interceptor;

import com.chenjin.testPCQueue.common.entity.BaseEntity;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

public class BaseEntityInterceptor extends EmptyInterceptor
{
    private static final long serialVersionUID = -32011188847380852L;

    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types)
    {
        Map userM = getCurrentUser();
        if ((entity instanceof BaseEntity)) {
            int indexOf = ArrayUtils.indexOf(propertyNames, "createDate");
            state[indexOf] = new Date();
            if (((BaseEntity)entity).getCreateUser() == null) {
                indexOf = ArrayUtils.indexOf(propertyNames, "createUser");
                state[indexOf] = userM.get("empId");
            }
            if (((BaseEntity)entity).getCreateName() == null) {
                indexOf = ArrayUtils.indexOf(propertyNames, "createName");
                state[indexOf] = userM.get("name");
            }

            indexOf = ArrayUtils.indexOf(propertyNames, "modifyDate");
            state[indexOf] = new Date();

            indexOf = ArrayUtils.indexOf(propertyNames, "modifyUser");
            state[indexOf] = userM.get("empId");

            indexOf = ArrayUtils.indexOf(propertyNames, "modifyName");
            state[indexOf] = userM.get("name");

            return true;
        }
        return false;
    }

    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types)
    {
        Map userM = getCurrentUser();
        if ((entity instanceof BaseEntity)) {
            int indexOf = ArrayUtils.indexOf(propertyNames, "modifyDate");
            currentState[indexOf] = new Date();

            indexOf = ArrayUtils.indexOf(propertyNames, "modifyUser");
            currentState[indexOf] = userM.get("empId");

            indexOf = ArrayUtils.indexOf(propertyNames, "modifyName");
            currentState[indexOf] = userM.get("name");

            return true;
        }
        return false;
    }

    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types)
    {
    }

    private Map<String, String> getCurrentUser()
    {
        Map userM = new HashMap();
        try {
            Subject subject = SecurityUtils.getSubject();
            if ((subject != null) &&
                    (subject.getPrincipal() != null)) {
                String user = (String)subject.getPrincipal();
                userM.put("empId", user.split(",")[0]);
                userM.put("name", user.split(",")[1]);
            }
        }
        catch (Exception e) {
            userM.put("empId", "system");
            userM.put("name", "系统管理员");
        }

        return userM;
    }
}