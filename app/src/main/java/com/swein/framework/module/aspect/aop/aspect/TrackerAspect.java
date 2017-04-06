package com.swein.framework.module.aspect.aop.aspect;

import com.swein.framework.module.aspect.analytics.manager.TrackerManager;
import com.swein.framework.module.aspect.aop.report.view.ViewReport;
import com.swein.framework.module.aspect.aop.sender.Sender;
import com.swein.framework.tools.util.debug.log.ILog;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.lang.reflect.SourceLocation;

import static com.swein.framework.module.aspect.aop.report.event.EventReport.createEventReport;

/**
 *
 * Aspect representing the cross cutting-concern: Method and Constructor Tracing.
 *  * Created by seokho on 03/04/2017.
 */

@Aspect
public class TrackerAspect {

    /**
     *
     * for view enter log
     *
     */
    private static final String POINTCUT_CONSTRUCTOR_VIEW_TRACE =
            "execution(@ViewTrace *.new(..))";

    private static final String POINTCUT_METHOD_VIEW_TRACE =
            "execution(@ViewTrace * *(..))";

    @Pointcut(POINTCUT_CONSTRUCTOR_VIEW_TRACE)
    public void constructorAnnotatedGAViewTrace() {}

    @Pointcut( POINTCUT_METHOD_VIEW_TRACE )
    public void methodAnnotatedWithGAViewTrace() {}

    @Around("methodAnnotatedWithViewTrace() && constructorAnnotatedGAViewTrace()")
    public Object weaveJoinViewPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String          className       = methodSignature.getDeclaringType().getSimpleName();
        String          methodName      = methodSignature.getName();

        String result = "View Enter Track";

        Sender.sendViewReport( className, ViewReport.createViewReport( methodName, result));

        return result;
    }





    /**
     *
     * for action event log
     *
     */
    private static final String POINTCUT_METHOD_EVENT_TRACE =
            "execution(@EventTrace * *(..))";

    private static final String POINTCUT_CONSTRUCTOR_EVENT_TRACE =
            "execution(@EventTrace *.new(..))";


    @Pointcut( POINTCUT_METHOD_EVENT_TRACE )
    public void methodAnnotatedWithGAEventTrace() {}

    @Pointcut(POINTCUT_CONSTRUCTOR_EVENT_TRACE)
    public void constructorAnnotatedGAEventTrace() {}

    @Around("methodAnnotatedWithEventTrace() && constructorAnnotatedGAEventTrace()")
    public Object weaveJoinEventPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String          className       = methodSignature.getDeclaringType().getSimpleName();
        String          methodName      = methodSignature.getName();

        String result = "Event Action Track";

        Sender.sendEventReport( className, createEventReport( methodName, result));

        return result;
    }





    /**
     * catch try - catch block only and speed
     */
    private static final String POINTCUT_ALL_TRY_CATCH_EXCEPTION_TRACE = "handler(java.lang.*Exception)";

    @Before(POINTCUT_ALL_TRY_CATCH_EXCEPTION_TRACE)
    public void allTryCatchException(JoinPoint joinPoint){

        String exception = "[ ---------- try catch exception ---------- >>>> ";

        ILog.iLogDebug( "this ", joinPoint.getSignature().getDeclaringType().getSimpleName() + " " + joinPoint.getSignature().getName()
        + " " + joinPoint.getSourceLocation().toString() + " " + joinPoint.getKind());

        Object[] args = joinPoint.getArgs();
        for(Object object : args) {
            ILog.iLogDebug( "args ", object.toString() );
            exception += object.toString();
        }

        exception += "]\n###location ------ >>>> ";

        SourceLocation location = joinPoint.getSourceLocation();
        ILog.iLogDebug( "location", location.getFileName() + " " + location.getLine() + " " + location.getWithinType() );

        exception += location;

        exception += "\n";

        TrackerManager.sendTryCatchExceptionReport( exception, joinPoint.getSignature().getDeclaringType().getSimpleName(), true, false);

    }



    /**
     *  catch all exception but slow
     *  not suggest use this method because it join into every pointcut and maybe will make app slow
     */
//    private static final String POINTCUT_ALL_EXCEPTION_TRACE =
//            "call(* *..*(..))";
//
//    @AfterThrowing(pointcut = POINTCUT_ALL_EXCEPTION_TRACE, throwing = "throwable")
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

}
