package com.xindian.beanutils.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 声明一个类型,
 * 
 * 被声明的类型必须是Filed Type的子类
 * 
 * @author Elva
 * @date 2011-1-20
 * @version 1.0
 */
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface Type
{
	Class<?>[] value();
}
