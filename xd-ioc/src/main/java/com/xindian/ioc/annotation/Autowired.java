package com.xindian.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 按照类型自动装配
 * 
 * @author Elva
 * @date 2011-1-24
 * @version 1.0
 */
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface Autowired
{
	/**
	 * 如果这个值为false,当无法得到对象或者注入的时候会抛出异常
	 * 
	 * @return
	 */
	boolean required() default true;
}
