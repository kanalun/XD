package com.xindian.mvc.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class CN
{
	enum IdNumberType
	{
		ALL, L15, L18
	}

	/**
	 * 验证是否为合法的省份证号码15/18
	 * 
	 * @author Elva
	 * @date 2011-2-7
	 * @version 1.0
	 */
	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface IdNumber
	{
		IdNumberType type() default IdNumberType.ALL;
	}

	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface Mobile
	{
	}
}
