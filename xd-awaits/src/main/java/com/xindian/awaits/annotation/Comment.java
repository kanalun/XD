package com.xindian.awaits.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 备注
 * 
 * @author QCC
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Comment
{
	String value();

	/** 是否为内部字段 */
	boolean internal() default false;
}
