package com.swein.framework.module.aop.networkcheck;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.network.NetWorkUtil;
import com.swein.framework.tools.util.toast.ToastUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class NetworkCheckAspectJ {

    private static final String TAG = "NetworkCheckAspectJ";

    @Pointcut("execution(@com.swein.framework.module.aop.networkcheck.NetworkCheckAnnotation  * *(..))")
    public void executionNetworkCheck() {
    }

    @Around("executionNetworkCheck()")
    public Object networkCheck(ProceedingJoinPoint joinPoint) throws Throwable {

        ILog.iLogDebug(TAG, "networkCheck");

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        NetworkCheckAnnotation networkCheckAnnotation = signature.getMethod().getAnnotation(NetworkCheckAnnotation.class);
        if (networkCheckAnnotation != null) {

            Context context = (Context) joinPoint.getThis();

            if (context != null) {

                if (!NetWorkUtil.isNetworkConnected(context)) {
                    ILog.iLogDebug(TAG, "no net work");
                    ToastUtil.showCustomShortToastNormal(context, "check your network");
                    return null;
                }
                else {
                    ILog.iLogDebug(TAG, "net work is good");
                    ToastUtil.showCustomShortToastNormal(context, "net work is good");
                    return joinPoint.proceed();
                }

            }
        }
        return joinPoint.proceed();
    }

    private Context getContext(Object object) {

        if (object instanceof Activity) {
            return (Activity) object;

        }
        else if (object instanceof Fragment) {

            Fragment fragment = (Fragment) object;
            return fragment.getActivity();

        }
        else if (object instanceof View) {

            View view = (View) object;
            return view.getContext();

        }

        return null;
    }

}
