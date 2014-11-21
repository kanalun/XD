package com.xindian.mvc.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类型验证
 * 
 * @author Elva
 * @date 2011-2-7
 * @version 1.0
 */
public interface TypeValidation
{
	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface IsType
	{
		Class<?>[] value();// 检查是否为其中某一个类型

		// autoboxing
		boolean primitive() default true;// 基本类型,默认为相同
	}

	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface IsArray// 是否为某以类型的数组(对分割后字符串进行类型转换)
	{
		Class<?>[] value() default Object.class;

		String x() default ",";// 分隔符
	}

	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface IsBoolean
	{

	}

	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface IsNumeric// Number
	{

	}

	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface IsByte
	{

	}

	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface IsChar
	{
	}

	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface IsInt
	{
	}

	/**
	 * 可以转化成
	 * 
	 * @author Elva
	 * @date 2011-2-7
	 * @version 1.0
	 */
	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface IsLong
	{
		long min() default Long.MIN_VALUE;

		long max() default Long.MAX_VALUE;
	}

	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface IsFloat
	{

	}

	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface IsDouble
	{

	}

	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface IsTime
	{

	}

	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface IsDate
	{

	}

	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface IsDateTime
	{

	}

	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface IsBigInteger
	{

	}

	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface IsBigDecimal
	{

	}
}
