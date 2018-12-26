package com.chenjin.common.framework.aspect;

import com.chenjin.common.entity.BasicEntity;
import com.chenjin.common.framework.annotation.ProjectCodeFlag;
import com.chenjin.common.util.ThreadLocalUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

public class DataSourceAspect
{
    private DSParams dSParams;

    public DSParams getdSParams()
    {
        return this.dSParams;
    }

    public void setdSParams(DSParams dSParams) {
        this.dSParams = dSParams;
    }

    public Object controlTarget(ProceedingJoinPoint joinPoint)
            throws Throwable
    {
        System.out.println("环绕通知方法 参数为：" + Arrays.toString(joinPoint.getArgs()));

        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        Method method = methodSignature.getMethod();
        System.out.println(method);
        Object obj = null;
        Object[] args = joinPoint.getArgs();
        int argsCount = args.length;
        boolean rslt = false;
        String projectCode = "";

        if (argsCount == 0) {
            rslt = false;
            System.out.println("0个参数");
        }

        if (argsCount == 1) {
            Object o = args[0];
            System.out.println("1个参数");
            if (o != null) {
                if (String.class.isAssignableFrom(o.getClass())) {
                    int index = getProjectCodeIndex(method);
                    if ((index == 0) && (!StringUtils.isEmpty(o.toString()))) {
                        rslt = true;
                        projectCode = o.toString();
                    }
                } else if (BasicEntity.class.isAssignableFrom(o.getClass())) {
                    BasicEntity be = (BasicEntity)o;
                    if (!StringUtils.isEmpty(be.getProjectCode())) {
                        rslt = true;
                        projectCode = be.getProjectCode();
                    }
                }

            }

        }

        if (argsCount > 1) {
            System.out.println("n个参数");
            int index = getProjectCodeIndex(method);
            if (index >= 0) {
                Object o = args[index];
                if ((o != null) && (!StringUtils.isEmpty(o.toString())) &&
                        (String.class.isAssignableFrom(o.getClass()))) {
                    rslt = true;
                    projectCode = o.toString();
                }
            }
        }

        System.out.println(rslt);
        System.out.println("pj=" + projectCode);
        if (rslt) {
            if (this.dSParams.getDSName(projectCode) == null)
            {
                this.dSParams.init();

                if (this.dSParams.getDSName(projectCode) == null) {
                    throw new Exception(projectCode + "数据源未设置正确");
                }
            }
            ThreadLocalUtil.setDataSource(this.dSParams.getDSName(projectCode));
            obj = joinPoint.proceed();
        } else {
            throw new Exception("projectCode参数未传入");
        }
        return obj;
    }

    private int getProjectCodeIndex(Method method) {
        int index = -1;
        Annotation[][] an = method.getParameterAnnotations();
        for (int i = 0; i < an.length; i++) {
            for (Annotation annotation : an[i]) {
                if (ProjectCodeFlag.class.isAssignableFrom(annotation.annotationType())) {
                    index = i;
                    break;
                }
            }
        }

        return index;
    }
}