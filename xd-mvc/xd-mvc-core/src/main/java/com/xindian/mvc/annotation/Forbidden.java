package com.xindian.mvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.xindian.mvc.exception.ErrorCodeException;
import com.xindian.mvc.exception.ForbiddenException;

/**
 * 使用该注解表示被注解该的方法是"禁止访问"的!,默认服务器使用403禁止访问
 * 
 * @author Elva
 * 
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Forbidden
{
	int value() default 403;

	String message() default "Action or Method is Forbidden";

	Class<? extends ErrorCodeException> exception() default ForbiddenException.class;
}
