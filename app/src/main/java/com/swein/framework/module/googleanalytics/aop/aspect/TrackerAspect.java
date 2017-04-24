package com.swein.framework.module.googleanalytics.aop.aspect;

import com.swein.framework.module.googleanalytics.aop.monitor.processtimer.ProcessTimer;
import com.swein.framework.module.googleanalytics.aop.monitor.processtimer.ProcessTimerLog;
import com.swein.framework.module.googleanalytics.aop.report.view.ViewReport;
import com.swein.framework.module.googleanalytics.aop.sender.Sender;
import com.swein.framework.module.googleanalytics.manager.TrackerManager;
import com.swein.framework.tools.util.debug.log.ILog;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.lang.reflect.SourceLocation;

import static com.swein.framework.module.googleanalytics.aop.report.event.EventReport.createEventReport;

/**
 *
 * Aspect representing the cross cutting-concern: Method and Constructor Tracing.
 *  * Created by seokho on 03/04/2017.
 */

@Aspect
public class TrackerAspect {


    /**
     *  Method process time measuring
     */
    private static final String POINTCUT_METHOD =
            "execution(@com.swein.framework.module.googleanalytics.aop.monitor.processtimer.TimerTrace * *(..))";

    private static final String POINTCUT_CONSTRUCTOR =
            "execution(@com.swein.framework.module.googleanalytics.aop.monitor.processtimer.TimerTrace *.new(..))";

    @Pointcut(POINTCUT_METHOD)
    public void methodAnnotatedWithProcessTimerTrace() {}

    @Pointcut(POINTCUT_CONSTRUCTOR)
    public void constructorAnnotatedProcessTimerTrace() {}

    @Around("methodAnnotatedWithProcessTimerTrace() || constructorAnnotatedProcessTimerTrace()")
    public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        final ProcessTimer processTimer = new ProcessTimer();
        processTimer.start();
        Object result = joinPoint.proceed();
        processTimer.stop();

        ProcessTimerLog.log( className, processTimerMessage( className, methodName, processTimer.getTotalTimeMillis()));

        return result;
    }

    /**
     * Create a log message.
     *
     * @param methodName A string with the method name.
     * @param methodDuration Duration of the method in milliseconds.
     * @return A string representing message.
     */
    private static String processTimerMessage(String className, String methodName, long methodDuration) {
        StringBuilder message = new StringBuilder();
        message.append("process time --> ");
        message.append(className);
        message.append(":");
        message.append(methodName);
        message.append("  ");
        message.append("[");
        message.append(methodDuration);
        message.append("ms");
        message.append("]");

        return message.toString();
    }

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

        ILog.iLogDebug( "try catch exception ", joinPoint.getSignature().getDeclaringType().getSimpleName() + " " + joinPoint.getSignature().getName()
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
     * catch try - catch block only and speed
     */
    private static final String POINTCUT_ALL_TRY_CATCH_OUT_OF_MEMORY_TRACE = "handler(java.lang.OutOfMemoryError)";

    @Before(POINTCUT_ALL_TRY_CATCH_OUT_OF_MEMORY_TRACE)
    public void allTryCatchOutOfMemoryError(JoinPoint joinPoint){

        SourceLocation location = joinPoint.getSourceLocation();

        String exception = "[ ---------- try catch exception ---------- >>>> ";

        ILog.iLogDebug( "try catch exception ", "1 " + joinPoint.getSignature().getDeclaringType().getSimpleName() + " 2 " + joinPoint.getSignature().getName()
                + " 3 " + joinPoint.getSourceLocation().toString() + " 4 " + joinPoint.getKind());

        Object[] args = joinPoint.getArgs();
        for(Object object : args) {
            ILog.iLogDebug( "args ", object.toString() );
            exception += object.toString();
        }

        exception += "]\n###location ------ >>>> ";

        ILog.iLogDebug( "location", location.getFileName() + " 1 " + location.getLine() + " 2 " + location.getWithinType() );

        exception += location;

        exception += "\n";

        TrackerManager.sendTryCatchExceptionReport( exception, joinPoint.getSignature().getDeclaringType().getSimpleName(), true, false );
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
//        ILog.iLogDebug( "all exception ", "joinPoint " + joinPoint.getSignature().getDeclaringType().getSimpleName() + " " + joinPoint.getSignature().getName());
//
//        ILog.iLogDebug( "all exception ", "hurtThrows: " + throwable.getMessage() + " " + throwable.getLocalizedMessage() + " " + throwable.getCause() + " " );
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
