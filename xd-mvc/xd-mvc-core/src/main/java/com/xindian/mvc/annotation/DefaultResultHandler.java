package com.xindian.mvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 默认处理方式,如果系统无法解析,则采用...
 * 
 * @author Elva
 * 
 */
@Target({ ElementType.PACKAGE, ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DefaultResultHandler
{
	// Global
	Class<? extends com.xindian.mvc.result.ResultHandler> value();
}
