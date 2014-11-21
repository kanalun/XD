package com.xindian.mvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.xindian.mvc.result.ResultHandler;

/**
 * 为package,Action',Action methods设置"返回结果处理"
 * 
 * @author Elva
 * @date 2011-1-24
 * @version 1.0
 */
@Target({ ElementType.PACKAGE, ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Result
{
	String name();// 返回名称

	String value();// 值

	Class<? extends ResultHandler> type() default ResultHandler.class;// 处理类型

	String protocol() default "forward";// 协议

	String[] parameters() default {};
}
