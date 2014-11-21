package com.xindian.awaits.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表注释
 * 
 * @author QCC
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Table
{
	/** Table名称,默认情况下将使用类的简单名称作为表的名称 */
	String name() default "";

	/** 表注释 */
	String comment() default "";

	String catalog() default "";

	String schema() default "";
}
