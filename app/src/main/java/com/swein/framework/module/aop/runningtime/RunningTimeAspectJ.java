package com.swein.framework.module.aop.runningtime;

import com.swein.framework.tools.util.debug.log.ILog;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class RunningTimeAspectJ {

    private static final String TAG = "RunningTimeAspectJ";

    @Pointcut("execution(@com.swein.framework.module.aop.runningtime.RunningTime  * *(..))")
    public void executionRunningTime() {
    }

    @Around("executionRunningTime()")
    public Object runningTime(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        String methodName = methodSignature.getName();
        String className = joinPoint.getThis().getClass().getSimpleName();

        long before = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long after = System.currentTimeMillis();

        ILog.iLogDebug(TAG, className + " " + methodName + " finish in: " + (after - before) + "ms");

        return result;
    }

}
