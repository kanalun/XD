package com.xindian.mvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解一个类型为Action
 * 
 * @author Elva
 * @date 2011-1-24
 * @version 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Action
{
	/** Action的名字空间,除非在配置文件中另外指定否则为"%WebRoot%/" */
	String namespace() default "/";

	/** Action的名称,默认为类的SimpleName */
	String name() default "";

	/** Action的默认方法,可以再配置文件中写,否则为execute */
	String defaultMethod() default "";

	/** 是否过滤Action的Getter/Setter,默认为true */
	boolean filterGetterSetter() default true;
}
