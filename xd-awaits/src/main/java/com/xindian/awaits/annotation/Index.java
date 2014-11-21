package com.xindian.awaits.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 索引:新加入没有使用
 * 
 * @author Elva
 * @date 2013-10-22
 * @version 1.0.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Index
{
	/**
	 * 索引名称:默认为:INDEX_表明_字段名称
	 * 
	 * @return
	 */
	String name() default "";
}
