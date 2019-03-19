package com.chenjin.testPCQueue.common.framework.interceptor;

import com.chenjin.testPCQueue.common.entity.BaseEntity;
import com.chenjin.testPCQueue.common.entity.BasicEntity;
import com.chenjin.testPCQueue.common.util.ThreadLocalUtil;
import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.ArrayUtils;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

public class BasicEntityInterceptor extends EmptyInterceptor
{
    private static final long serialVersionUID = -6507706422623983435L;

    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types)
    {
        if (((entity instanceof BasicEntity)) || ((entity instanceof BaseEntity))) {
            int indexOf = ArrayUtils.indexOf(propertyNames, "createDate");
            state[indexOf] = new Date();

            indexOf = ArrayUtils.indexOf(propertyNames, "createUser");
            state[indexOf] = ThreadLocalUtil.getSysUser();

            indexOf = ArrayUtils.indexOf(propertyNames, "createName");
            state[indexOf] = ThreadLocalUtil.getSysName();

            return true;
        }

        return false;
    }

    public boolean onFlushDirty(Object entity, Serializable id, Object[] state, Object[] previousState, String[] propertyNames, Type[] types)
    {
        if (((entity instanceof BasicEntity)) || ((entity instanceof BaseEntity))) {
            int indexOf = ArrayUtils.indexOf(propertyNames, "modifyDate");
            state[indexOf] = new Date();

            indexOf = ArrayUtils.indexOf(propertyNames, "modifyUser");
            state[indexOf] = ThreadLocalUtil.getSysUser();

            indexOf = ArrayUtils.indexOf(propertyNames, "modifyName");
            state[indexOf] = ThreadLocalUtil.getSysName();

            return true;
        }
        return false;
    }

    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types)
    {
    }
}