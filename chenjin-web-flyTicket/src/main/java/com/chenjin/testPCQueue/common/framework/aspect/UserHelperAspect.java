package com.chenjin.testPCQueue.common.framework.aspect;

import com.chenjin.testPCQueue.common.entity.UserHelper;
import com.chenjin.testPCQueue.common.framework.annotation.ProjectCodeFlag;
import com.chenjin.testPCQueue.common.util.ThreadLocalUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

public class UserHelperAspect
{
    private DSParams dSParams;
    private Boolean oneDS = Boolean.valueOf(true);

    public DSParams getdSParams()
    {
        return this.dSParams;
    }

    public void setdSParams(DSParams dSParams) {
        this.dSParams = dSParams;
    }

    public Boolean getOneDS()
    {
        return this.oneDS;
    }

    public void setOneDS(Boolean oneDS) {
        this.oneDS = oneDS;
    }

    public Object controlTarget(ProceedingJoinPoint joinPoint)
            throws Throwable
    {
        System.out.println("UserHelperAspect环绕通知方法");

        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        Method method = methodSignature.getMethod();
        System.out.println(joinPoint.getTarget());
        System.out.println(method);
        Object obj = null;
        Object[] args = joinPoint.getArgs();
        int argsCount = args.length;
        boolean rslt = false;
        String projectCode = "";
        UserHelper userHelper = null;

        if (argsCount != 0) {
            if (argsCount == 1) {
                Object o = args[0];
                if ((o != null) &&
                        (UserHelper.class.isAssignableFrom(o.getClass()))) {
                    userHelper = (UserHelper)o;
                    rslt = true;
                    projectCode = userHelper.getProjectCode();
                }

            }
            else if (argsCount > 1) {
                int index = getProjectCodeIndex(method);
                if (index >= 0) {
                    Object o = args[index];
                    if ((o != null) && (UserHelper.class.isAssignableFrom(o.getClass()))) {
                        userHelper = (UserHelper)o;
                        rslt = true;
                        projectCode = userHelper.getProjectCode();
                    }
                }
            }

        }

        System.out.println("pj=" + projectCode);
        if (rslt) {
            if (!this.oneDS.booleanValue()) {
                if (this.dSParams.getDSName(projectCode) == null)
                {
                    this.dSParams.init();

                    if (this.dSParams.getDSName(projectCode) == null) {
                        throw new Exception(projectCode + "数据源未设置正确");
                    }
                }
                ThreadLocalUtil.setDataSource(this.dSParams.getDSName(projectCode));
            }

            ThreadLocalUtil.setSysUser(userHelper.getSysUser());
            ThreadLocalUtil.setSysName(userHelper.getSysName());

            obj = joinPoint.proceed();
        } else {
            throw new Exception(joinPoint.getTarget() + "[" + method.getName() + "]" + "UserHelper参数未传入");
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