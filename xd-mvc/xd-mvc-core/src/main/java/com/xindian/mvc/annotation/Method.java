package com.xindian.mvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Elva
 * @version 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Method
{
	String name() default "";

	// 是否为Action的默认方法,一个Action只能有一个默认方法
	// boolean isDefault() default false;

	MethodType type() default MethodType.ALL;
}
