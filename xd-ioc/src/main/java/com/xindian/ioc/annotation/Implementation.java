package com.xindian.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 声明接口或者抽象类使用什么实现
 * 
 * @author Elva
 * @date 2011-1-24
 * @version 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Implementation
{
	Class<?> value();
}
