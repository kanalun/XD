package com.xindian.mvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 将模板与方法绑定,
 * 
 * 从而实现访问模板路前运行HOOK方法
 * 
 * @author Elva
 * @date 2011-1-17
 * @version 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Hook
{
	String value();

	Dispatcher[] dispatcher() default { Dispatcher.REQUEST, Dispatcher.FORWARD };
}
