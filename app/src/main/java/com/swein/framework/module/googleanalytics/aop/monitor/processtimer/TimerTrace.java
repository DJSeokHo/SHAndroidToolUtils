package com.swein.framework.module.googleanalytics.aop.monitor.processtimer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by seokho on 24/04/2017.
 */

@Retention( RetentionPolicy.CLASS)
@Target({ ElementType.CONSTRUCTOR, ElementType.METHOD })
public @interface TimerTrace {
}
