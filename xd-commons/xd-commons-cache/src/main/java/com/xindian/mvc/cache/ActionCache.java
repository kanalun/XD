package com.xindian.mvc.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 声明这个Action中使用什么名称的缓存
 * 
 * @author Elva
 * @date 2011-3-10
 * @version 1.0
 */
@Target({ ElementType.PACKAGE, ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ActionCache
{
	String name();
}
