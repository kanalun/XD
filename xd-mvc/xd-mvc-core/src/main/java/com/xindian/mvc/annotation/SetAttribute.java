package com.xindian.mvc.annotation;

/**
 * 将某个属性放到session/ssss
 * 
 * @author Elva
 * @date 2011-2-7
 * @version 1.0
 */
public @interface SetAttribute
{
	String key() default "";

	String scope() default "request";
}
