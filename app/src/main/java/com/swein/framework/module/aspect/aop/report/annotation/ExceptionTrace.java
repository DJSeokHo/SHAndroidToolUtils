package com.swein.framework.module.aspect.aop.report.annotation;

/**
 * Created by seokho on 03/04/2017.
 */


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * Indicates that the annotated method is being traced (debug mode only) and
 * will use {@link android.util.Log} to print debug data:
 * - Method name
 * - Total execution time
 * - Value (optional string parameter)
 *
 *  this class is track view movement
 *
 * Created by seokho on 31/03/2017.
 */

@Retention( RetentionPolicy.CLASS)
@Target({ ElementType.METHOD})
public @interface ExceptionTrace {}
