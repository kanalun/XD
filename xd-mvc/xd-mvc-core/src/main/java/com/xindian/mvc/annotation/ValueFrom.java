package com.xindian.mvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 告诉框架,值来自于什么地反,requestAttribute,session,application,
 * 
 * @author Elva
 * @date 2011-3-14
 * @version 1.0
 */
@Target({ ElementType.PACKAGE, ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValueFrom
{
	String[] scrope() default "p";
}
