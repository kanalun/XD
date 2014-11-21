package com.xindian.mvc.conversion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author Elva
 * @date 2011-2-13
 * @version 1.0
 */
public interface Conversion
{
	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface DefaultLocale
	{
		String value();
	}

	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface DefaultTimeZone
	{
		String value();
	}

	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface DefaultDateFormats
	{
		String[] value();
	}

	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface DefaultDelimiter
	{
		String value() default ",";
	}

	/**
	 * 转换的时候对于字符串是否进行Trim,字符串转换为类型的时候
	 * 
	 * @author Elva
	 * @date 2011-2-13
	 * @version 1.0
	 */
	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface Trim
	{
		boolean value() default true;
	}

	/**
	 * 
	 * 对于数组/List/Set..类型转换为String类型,
	 * 
	 * 默认的方式是将容器中的每一个元素.toString之后用分隔符分割后"相加"
	 * 
	 * 使用OnlyFirstToString来改变这种行为,我们只获取容器的第一个元素获得它的String值,而忽略容器中其他的元素
	 * 
	 * @author Elva
	 * @date 2011-2-14
	 * @version 1.0
	 */
	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface OnlyFirstToString
	{
		boolean value() default true;
	}

	/**
	 * 在对象/或者对象数组转放到Map的时候用来做Map的key
	 * 
	 * @author Elva
	 * @date 2011-2-14
	 * @version 1.0
	 */
	@Target({ ElementType.METHOD, ElementType.FIELD })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface MapKey
	{

	}
}
