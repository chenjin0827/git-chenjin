package com.chenjin.common.framework.util;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

public class CollectionToStringUserType
        implements UserType, ParameterizedType, Serializable
{
    private static final long serialVersionUID = -6852818238062510415L;
    private String separator;
    private Class elementType;
    private Class collectionType;

    public void setParameterValues(Properties parameters)
    {
        String separator = (String)parameters.get("separator");
        if (!StringUtils.isEmpty(separator))
            this.separator = separator;
        else {
            this.separator = ",";
        }

        String collectionType = (String)parameters.get("collectionType");
        if (!StringUtils.isEmpty(collectionType))
            try {
                this.collectionType = Class.forName(collectionType);
            } catch (ClassNotFoundException e) {
                throw new HibernateException(e);
            }
        else {
            this.collectionType = ArrayList.class;
        }

        String elementType = (String)parameters.get("elementType");
        if (!StringUtils.isEmpty(elementType))
            try {
                this.elementType = Class.forName(elementType);
            } catch (ClassNotFoundException e) {
                throw new HibernateException(e);
            }
        else
            this.elementType = Long.TYPE;
    }

    public int[] sqlTypes()
    {
        return new int[] { 12 };
    }

    public Class returnedClass()
    {
        return this.collectionType;
    }

    public boolean equals(Object o, Object o1) throws HibernateException
    {
        if (o == o1) {
            return true;
        }
        if ((o == null) || (o == null)) {
            return false;
        }

        return o.equals(o1);
    }

    public int hashCode(Object o) throws HibernateException
    {
        return o.hashCode();
    }

    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
            throws HibernateException, SQLException
    {
        String valueStr = rs.getString(names[0]);
        if (StringUtils.isEmpty(valueStr)) {
            return newCollection();
        }

        String[] values = StringUtils.split(valueStr, this.separator);

        Collection result = newCollection();

        for (String value : values) {
            if (StringUtils.isNotEmpty(value)) {
                result.add(ConvertUtils.convert(value, this.elementType));
            }
        }
        return result;
    }

    private Collection newCollection()
    {
        try {
            return (Collection)this.collectionType.newInstance(); } catch (Exception e) {
        }
        throw new HibernateException("HibernateException");
    }

    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
            throws HibernateException, SQLException
    {
        String valueStr;
        if (value == null)
            valueStr = "";
        else {
            valueStr = StringUtils.join((Collection)value, this.separator);
        }
        if (StringUtils.isNotEmpty(valueStr)) {
            valueStr = valueStr + ",";
        }
        st.setString(index, valueStr);
    }

    public Object deepCopy(Object o)
            throws HibernateException
    {
        if (o == null) return null;
        Collection copyCollection = newCollection();
        copyCollection.addAll((Collection)o);
        return copyCollection;
    }

    public boolean isMutable()
    {
        return true;
    }

    public Serializable disassemble(Object value)
            throws HibernateException
    {
        return (Serializable)value;
    }

    public Object assemble(Serializable cached, Object owner)
            throws HibernateException
    {
        return cached;
    }

    public Object replace(Object original, Object target, Object owner) throws HibernateException
    {
        return original;
    }
}