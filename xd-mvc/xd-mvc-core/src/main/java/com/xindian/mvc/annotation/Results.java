package com.xindian.mvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 为package,Action',Action methods设置多个"返回结果处理"
 * 
 * @author Elva
 * @date 2011-1-24
 * @version 1.0
 */
@Target({ ElementType.PACKAGE, ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Results
{
	Result[] value() default {};
}
