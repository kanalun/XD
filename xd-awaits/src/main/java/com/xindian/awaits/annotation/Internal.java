package com.xindian.awaits.annotation;

/**
 * 内部字段
 * 
 * @author QCC
 */
public @interface Internal
{
	/** 是否为内部字段 */
	boolean value() default true;
}
