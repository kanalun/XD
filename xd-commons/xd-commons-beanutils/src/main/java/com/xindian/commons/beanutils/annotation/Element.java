package com.xindian.commons.beanutils.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 声明元素类型
 * 
 * 当Array/List/Map没有使用泛型,或者使用接口的时候用来指定List的元素类型和Map的Value类型
 * 
 * @author Elva
 * @date 2011-1-22
 * @version 1.0
 */
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface Element
{
	Class<?> value();
}
