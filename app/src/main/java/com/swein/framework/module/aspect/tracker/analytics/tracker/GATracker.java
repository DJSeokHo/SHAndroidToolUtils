package com.swein.framework.module.aspect.tracker.analytics.tracker;

import com.swein.framework.module.aspect.tracker.analytics.sender.Sender;
import com.swein.framework.tools.util.debug.log.ILog;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.lang.reflect.SourceLocation;

import static com.swein.framework.module.aspect.tracker.analytics.report.event.EventReport.createEventReport;
import static com.swein.framework.module.aspect.tracker.analytics.report.view.ViewReport.createViewReport;

/**
 *
 * Aspect representing the cross cutting-concern: Method and Constructor Tracing.
 *  * Created by seokho on 03/04/2017.
 */

@Aspect
public class GATracker {




    private static final String POINTCUT_METHOD_GA_VIEW_TRACE =
            "execution(@com.swein.framework.module.aspect.tracker.analytics.report.annotation.GAViewTrace * *(..))";

//    private static final String POINTCUT_CONSTRUCTOR_GA_VIEW_TRACE =
//            "execution(@com.swein.framework.module.aspect.tracker.analytics.report.annotation.GAViewTrace *.new(..))";

    @Pointcut(POINTCUT_METHOD_GA_VIEW_TRACE)
    public void methodAnnotatedWithGAViewTrace() {}

//    @Pointcut(POINTCUT_CONSTRUCTOR_GA_VIEW_TRACE)
//    public void constructorAnnotatedGAViewTrace() {}


    private static final String POINTCUT_METHOD_GA_EVENT_TRACE =
            "execution(@com.swein.framework.module.aspect.tracker.analytics.report.annotation.GAEventTrace * *(..))";

//    private static final String POINTCUT_CONSTRUCTOR_GA_EVENT_TRACE =
//            "execution(@com.swein.framework.module.aspect.tracker.analytics.report.annotation.GAEventTrace *.new(..))";

    @Pointcut(POINTCUT_METHOD_GA_EVENT_TRACE)
    public void methodAnnotatedWithGAEventTrace() {}

//    @Pointcut(POINTCUT_CONSTRUCTOR_GA_EVENT_TRACE)
//    public void constructorAnnotatedGAEventTrace() {}


//    private static final String POINTCUT_METHOD_GA_EXCEPTION_TRACE =
//            "within(@com.swein.shandroidtoolutils.MainActivity.testExceptionReport)";

//    private static final String POINTCUT_METHOD_GA_EXCEPTION_TRACE_H =
//            "handler(java.lang.Exception)";
//
//    private static final String POINTCUT_METHOD_GA_EXCEPTION_TRACE_IN =
//            "within(@com.swein.framework.module.aspect.tracker.analytics.report.annotation.GAExceptionTrace * *(..))";

//    private static final String POINTCUT_ALL_GA_EXCEPTION_TRACE =
//                "call(* *..*(..))";


    private static final String POINTCUT_ALL_GA_EXCEPTION_TRACE = "handler(java.lang.*Exception)";

//    @Pointcut(POINTCUT_METHOD_GA_EXCEPTION_TRACE)
//    public void methodAnnotatedWithGAExceptionTrace() {}

//    @Pointcut(POINTCUT_METHOD_GA_EXCEPTION_TRACE_H)
//    public void methodAnnotatedWithGAExceptionTraceH() {}

//    @Pointcut(POINTCUT_METHOD_GA_EXCEPTION_TRACE)
//    public void methodAnnotatedWithGAExceptionTrace() {}
//
//    @Pointcut(POINTCUT_METHOD_GA_EXCEPTION_TRACE_IN)
//    public void exceptionInCode(){}




    ////    private static final String POINTCUT_METHOD_GA_EXCEPTION_TRACE =
////            "execution(@com.swein.framework.module.aspect.tracker.analytics.report.annotation.GAExceptionTrace * *(..))";
////
////    private static final String POINTCUT_CONSTRUCTOR_GA_EXCEPTION_TRACE =
////            "execution(@com.swein.framework.module.aspect.tracker.analytics.report.annotation.GAExceptionTrace *.new(..))";
//
//





    @Around("methodAnnotatedWithGAViewTrace()")
    public Object weaveJoinViewPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String          className       = methodSignature.getDeclaringType().getSimpleName();
        String          methodName      = methodSignature.getName();

        String result = "Seok Ho View";

        Sender.sendViewReport( className, createViewReport( methodName, result));

        return result;
    }

    @Around("methodAnnotatedWithGAEventTrace()")
    public Object weaveJoinEventPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String          className       = methodSignature.getDeclaringType().getSimpleName();
        String          methodName      = methodSignature.getName();

        String result = "Seok Ho Event";

        Sender.sendEventReport( className, createEventReport( methodName, result));

        return result;
    }


    @Before(POINTCUT_ALL_GA_EXCEPTION_TRACE)
    public void allException(JoinPoint joinPoint){
        ILog.iLogDebug( "this ", joinPoint.getSignature().getDeclaringType().getSimpleName() + " " + joinPoint.getSignature().getName()
        + " " + joinPoint.getSourceLocation().toString() + " " + joinPoint.getKind());

        Object[] args = joinPoint.getArgs();
        for(Object object : args) {
            ILog.iLogDebug( "args", object.toString() );

        }

        SourceLocation location = joinPoint.getSourceLocation();
        ILog.iLogDebug( "location", location.getFileName() + " " + location.getLine() + " " + location.getWithinType() );
    }




//    @AfterThrowing(pointcut = POINTCUT_ALL_GA_EXCEPTION_TRACE, throwing = "throwable")
//    public void anyFuncThrows(JoinPoint joinPoint, Throwable throwable) {
//
//        ILog.iLogDebug( "this ", "joinPoint " + joinPoint.getSignature().getDeclaringType().getSimpleName() + " " + joinPoint.getSignature().getName());
//
//        ILog.iLogDebug( "this ", "hurtThrows: " + throwable.getMessage() + " " + throwable.getLocalizedMessage() + " " + throwable.getCause() + " " );
//
//        StackTraceElement[] stackTraceElement = throwable.getStackTrace();
//
//
//        for(StackTraceElement stackTraceElement1 : stackTraceElement) {
//            ILog.iLogDebug( "this", stackTraceElement1.toString() );
//        }
//
//    }


//    @AfterThrowing(pointcut = "methodAnnotatedWithGAExceptionTrace() && methodAnnotatedWithGAExceptionTraceH()", throwing = "throwable")
//    public Object weaveJoinExceptionPoint(JoinPoint joinPoint, Throwable throwable) {
//
//        ILog.iLogDebug( "this", "Exception caught : " + throwable + " on method : " + joinPoint.getSignature() );
//
//        if (joinPoint.getTarget() instanceof Activity ) {
//            if (throwable instanceof Exception) {
//
//                ILog.iLogDebug( "this 1", "Exception caught : " + throwable + " on method : " + joinPoint.getSignature() );
//
//            } else {
//
//                ILog.iLogDebug( "this 2", "Exception caught : " + throwable + " on method : " + joinPoint.getSignature() );
//
//            }
//        }
//
////        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
////        String          className       = methodSignature.getDeclaringType().getSimpleName();
////        String          methodName      = methodSignature.getName();
////
////        String result = "Seok Ho Exception";
////
////        Sender.sendExceptionReport( className, createExceptionReport( methodName, result));
////
////        return result;
//
//        return "";
//    }

}
