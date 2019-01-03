package com.chenjin.testSpringAop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

@Aspect
public class AspectJAdvice {
    /**
     * 前置通知：目标方法执行之前执行以下方法体的内容
     * @param jp
     */
   /* @Before("execution(* com.chenjin.testSpringAop.*.*(..))")
    public void beforeMethod(JoinPoint jp){
        String methodName = jp.getSignature().getName();
        System.out.println("【前置通知】the method 【" + methodName + "】 begins with " + Arrays.asList(jp.getArgs()));
    }

    *//**
     * 返回通知：目标方法正常执行完毕时执行以下代码
     * @param jp
     * @param result
     *//*
    @AfterReturning(value="execution(* com.chenjin.testSpringAop.*.*(..))",returning="result")
    public void afterReturningMethod(JoinPoint jp, Object result){
        String methodName = jp.getSignature().getName();
        System.out.println("【返回通知】the method 【" + methodName + "】 ends with 【" + result + "】");
    }

    *//**
     * 后置通知：目标方法执行之后执行以下方法体的内容，不管是否发生异常。
     * @param jp
     *//*
    @After("execution(* com.chenjin.testSpringAop.*.*(..))")
    public void afterMethod(JoinPoint jp){
        System.out.println("【后置通知】this is a afterMethod advice...");
    }

    *//**
     * 异常通知：目标方法发生异常的时候执行以下代码
     *//*
    @AfterThrowing(value="execution(* com.chenjin.testSpringAop.*.*(..))",throwing="e")
    public void afterThorwingMethod(JoinPoint jp, NullPointerException e){
        String methodName = jp.getSignature().getName();
        System.out.println("【异常通知】the method 【" + methodName + "】 occurs exception: " + e);
    }*/

      /**
   * 环绕通知：目标方法执行前后分别执行一些代码，发生异常的时候执行另外一些代码
   * @return
   */
  @Around(value="execution(* com.chenjin.testSpringAop.*.*(..))")
  public Object aroundMethod(ProceedingJoinPoint jp){
      String methodName = jp.getSignature().getName();
      Object result = null;
      try {
          System.out.println("【环绕通知中的--->前置通知】：the method 【" + methodName + "】 begins with " + Arrays.asList(jp.getArgs()));
          //执行目标方法
          result = jp.proceed();
          System.out.println("【环绕通知中的--->返回通知】：the method 【" + methodName + "】 ends with " + result);
      } catch (Throwable e) {
          System.out.println("【环绕通知中的--->异常通知】：the method 【" + methodName + "】 occurs exception " + e);
      }

      System.out.println("【环绕通知中的--->后置通知】：-----------------end.----------------------");
      return result;
  }

}
