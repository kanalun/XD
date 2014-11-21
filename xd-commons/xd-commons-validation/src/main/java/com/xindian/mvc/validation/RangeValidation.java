package com.xindian.mvc.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author Elva
 * @date 2011-2-7
 * @version 1.0
 */
public class RangeValidation
{
	/**
	 * 布尔值必须是true,否则无法通过在验证
	 * 
	 * 只对布尔类型有作用
	 * 
	 * @author Elva
	 * @date 2011-2-7
	 * @version 1.0
	 */
	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface MustTrue// IsTrue
	{

	}

	/**
	 * 布尔值必须是false,否则无法通过验证
	 * 
	 * @author Elva
	 * @date 2011-2-7
	 * @version 1.0
	 */
	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface MustFalse
	{

	}

	/**
	 * 布尔值必须为指定的值
	 * 
	 * @author Elva
	 * @date 2011-2-7
	 * @version 1.0
	 */
	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface BooleanRange
	{
		boolean value();
	}

	/**
	 * Byte必须在min和max之间(包含)
	 * 
	 * @author Elva
	 * @date 2011-2-7
	 * @version 1.0
	 */
	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface ByteRange
	{
		byte min() default Byte.MIN_VALUE;

		byte max() default Byte.MAX_VALUE;
	}

	/**
	 * 
	 * @author Elva
	 * @date 2011-2-7
	 * @version 1.0
	 */
	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface ShortRange
	{
		short min() default Short.MIN_VALUE;

		short max() default Short.MAX_VALUE;
	}

	/**
	 * 
	 * @author Elva
	 * @date 2011-2-7
	 * @version 1.0
	 */
	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface CharRange
	{
		char min() default Character.MIN_VALUE;

		char max() default Character.MAX_VALUE;
	}

	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface IntRange
	{
		int min() default Integer.MIN_VALUE;

		int max() default Integer.MAX_VALUE;
	}

	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface LongRange
	{
		long min() default Long.MIN_VALUE;

		long max() default Long.MAX_VALUE;
	}

	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface FloatRange
	{
		float min() default Float.MIN_VALUE;

		float max() default Float.MAX_VALUE;
	}

	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface DoubleRange
	{
		double min() default Double.MIN_VALUE;

		double max() default Double.MAX_VALUE;
	}

	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface DateRange
	{
		String min() default "1990-1-1";

		String max() default "";
	}
}
