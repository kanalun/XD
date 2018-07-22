package com.xindian.commons.i18n;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 常量接口
 * 
 * TODO 1,将数组和值的注解合并在一起
 * 
 * @author Elva
 * @date 2011-2-2
 * @version 1.0
 */
public interface Constants extends LocalizableResource
{
	/**
	 * 默认值可用于任何类型:类型会被转换到返回类型
	 * 
	 * @author Elva
	 * @date 2011-2-21
	 * @version 1.0
	 */
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface DefaultValue
	{
		String value();
	}

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface DefaultArrayValue
	{
		String[] value();
	}

	/**
	 * Default boolean value to be used if no translation is found
	 * 
	 * (and also used as the source for translation).
	 * 
	 * No quoting (other than normal Java string quoting) is done.
	 */
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface DefaultBooleanValue
	{
		boolean value();
	}

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface DefaultBooleanArrayValue
	{
		boolean[] value();
	}

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface DefaultByteValue
	{
		byte value();
	}

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface DefaultByteArrayValue
	{
		byte[] value();
	}

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface DefaultCharValue
	{
		char value();
	}

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface DefaultCharArrayValue
	{
		char[] value();
	}

	/**
	 * Default integer value to be used if no translation is found
	 * 
	 * (and also used as the source for translation).
	 * 
	 * No quoting (other than normal Java string quoting) is done.
	 */
	@Documented
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface DefaultShortValue
	{
		short value();
	}

	@Documented
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface DefaultShortArrayValue
	{
		short[] value();
	}

	/**
	 * Default integer value to be used if no translation is found
	 * 
	 * (and also used as the source for translation).
	 * 
	 * No quoting (other than normal Java string quoting) is done.
	 */
	@Documented
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface DefaultIntValue
	{
		int value();
	}

	@Documented
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface DefaultIntArrayValue
	{
		int[] value();
	}

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface DefaultLongValue
	{
		long value();
	}

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface DefaultLongArrayValue
	{
		long[] value();
	}

	/**
	 * Default float value to be used if no translation is found
	 * 
	 * (and also used as the source for translation).
	 * 
	 * No quoting (other than normal Java string quoting) is done.
	 */
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface DefaultFloatValue
	{
		float value();
	}

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface DefaultFloatArrayValue
	{
		float[] value();
	}

	/**
	 * Default double value to be used if no translation is found
	 * 
	 * (and also used as the source for translation).
	 * 
	 * No quoting (other than normal Java string quoting) is done.
	 */

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface DefaultDoubleValue
	{
		double value();
	}

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface DefaultDoubleArrayValue
	{
		double[] value();
	}

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface DefaultBigIntegerValue
	{
		String value();
	}

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface DefaultBigIntegerArrayValue
	{
		String[] value();
	}

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface DefaultBigDecimalValue
	{
		String value();
	}

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface DefaultBigDecimalArrayValue
	{
		String[] value();
	}

	/**
	 * Default string value to be used if no translation is found
	 * 
	 * (and also used as the source for translation).
	 * 
	 * No quoting (other than normal Java string quoting) is done.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@Documented
	public @interface DefaultStringValue
	{
		String value();
	}

	/**
	 * Default string array value to be used if no translation is found
	 * 
	 * (and also used as the source for translation).
	 * 
	 * No quoting (other than normal Java string quoting) is done.
	 * 
	 * Note that in the corresponding properties/etc file,
	 * 
	 * commas are used to separate elements of the array unless they are preceded
	 * 
	 * with a backslash.
	 */
	@Documented
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface DefaultStringArrayValue
	{
		String[] value();
	}

	/**
	 * Default string map value to be used if no translation is found
	 * 
	 * (and also used as the source for translation).
	 * 
	 * No quoting (other than normal Java string quoting) is done.
	 * 
	 * The strings for the map are supplied in key/value pairs.
	 * 
	 * Note that in the corresponding properties/etc file,
	 * 
	 * new keys can be supplied with the name of the method (or its corresponding key)
	 * 
	 * listing the set of keys for the map separated by commas
	 * 
	 * (commas can be part of the keys by preceding them with a backslash). In either case,
	 * 
	 * further entries have keys matching the key in this map.
	 * 
	 * key:value/key,value,k2,v2
	 */
	@Documented
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface DefaultStringMapValue
	{
		/**
		 * Must be key-value pairs.
		 */
		String[] value();
	}

	/**
	 * 默认枚举值
	 * 
	 * @author Elva
	 * @date 2011-2-20
	 * @version 1.0
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@Documented
	public @interface DefaultEnumValue
	{
		String value();
	}

	/**
	 * 默认枚举数组
	 * 
	 * @author Elva
	 * @date 2011-2-20
	 * @version 1.0
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@Documented
	public @interface DefaultEnumArrayValue
	{
		String[] value();
	}

	@Documented
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface DefaultClassValue
	{
		Class<?> value();
	}

	@Documented
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface DefaultClassArrayValue
	{
		Class<?>[] value();
	}
}
