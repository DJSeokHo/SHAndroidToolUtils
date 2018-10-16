package com.swein.framework.module.aop.authlogin;

import android.content.Context;

import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.toast.ToastUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class AuthLoginAspectJ {

    private static final String TAG = "AuthLoginAspectJ";

    @Pointcut("execution(@com.swein.framework.module.aop.authlogin.AuthLogin  * *(..))")
    public void executionAuthLogin() {
    }

    @Around("executionAuthLogin()")
    public Object authLogin(ProceedingJoinPoint joinPoint) throws Throwable {
        ILog.iLogDebug(TAG, "authLogin");

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AuthLogin authLogin = signature.getMethod().getAnnotation(AuthLogin.class);

        if (authLogin != null) {
            String userID = authLogin.userID();
            Context context = (Context) joinPoint.getThis();

            if ("sh".equals(userID)) {
                ILog.iLogDebug(TAG, "login success");
                ToastUtil.showCustomShortToastNormal(context, "login success");
                return joinPoint.proceed();
            }
            else {
                ILog.iLogDebug(TAG, "login please");
                ToastUtil.showCustomShortToastNormal(context, "login please");
                return null;
            }
        }
        return joinPoint.proceed();
    }

}
