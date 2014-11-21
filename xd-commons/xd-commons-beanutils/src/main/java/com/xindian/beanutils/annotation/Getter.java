package com.xindian.beanutils.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 为特定的Filed设定Getter方法
 * 
 * 建议使用标准的Getter方法
 * 
 * @author Elva
 * @date 2011-1-22
 * @version 1.0
 */
@Deprecated
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Getter
{
	String value();
}
