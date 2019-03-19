//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.chenjin.testPCQueue.common.cache.key;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;

public class DefaultKey implements Serializable {
    private static final long serialVersionUID = 1930236297081366076L;
    private String targetClassName;
    private String methodName;
    private Object[] params;
    private final int hashCode;

    public DefaultKey(Object target, Method method, Object[] elements) {
        this.targetClassName = target.getClass().getName();
        this.methodName = this.generatorMethodName(method);
        if (elements != null && elements.length != 0) {
            this.params = new Object[elements.length];

            for(int i = 0; i < elements.length; ++i) {
                Object ele = elements[i];
                this.params[i] = ele;
            }
        }

        this.hashCode = this.generatorHashCode();
    }

    private String generatorMethodName(Method method) {
        StringBuilder builder = new StringBuilder(method.getName());
        Class<?>[] types = method.getParameterTypes();
        if (types != null && types.length != 0) {
            builder.append("(");
            Class[] var4 = types;
            int var5 = types.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                Class<?> type = var4[var6];
                String name = type.getName();
                builder.append(name + ",");
            }

            builder.append(")");
        }

        return builder.toString();
    }

    private int generatorHashCode() {
//        int prime = true;
        int result = 1;
        result = 31 * result + this.hashCode;
        result = 31 * result + (this.methodName == null ? 0 : this.methodName.hashCode());
        result = 31 * result + Arrays.hashCode(this.params);
        result = 31 * result + (this.targetClassName == null ? 0 : this.targetClassName.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            DefaultKey other = (DefaultKey)obj;
            if (this.hashCode != other.hashCode) {
                return false;
            } else {
                if (this.methodName == null) {
                    if (other.methodName != null) {
                        return false;
                    }
                } else if (!this.methodName.equals(other.methodName)) {
                    return false;
                }

                if (!Arrays.equals(this.params, other.params)) {
                    return false;
                } else {
                    if (this.targetClassName == null) {
                        if (other.targetClassName != null) {
                            return false;
                        }
                    } else if (!this.targetClassName.equals(other.targetClassName)) {
                        return false;
                    }

                    return true;
                }
            }
        }
    }

    public final int hashCode() {
        return this.hashCode;
    }
}
