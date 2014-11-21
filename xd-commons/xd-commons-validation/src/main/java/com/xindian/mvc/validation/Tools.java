package com.xindian.mvc.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface Tools
{
	/**
	 * 字符串必须是Email格式
	 * 
	 * @author Elva
	 * @date 2011-2-7
	 * @version 1.0
	 */
	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface Email
	{

	}

	/**
	 * 字符串必须是Ipv4格式
	 * 
	 * @author Elva
	 * @date 2011-2-7
	 * @version 1.0
	 */
	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface Ipv4
	{

	}

	/**
	 * 字符串必须是Ipv6格式
	 * 
	 * @author Elva
	 * @date 2011-2-7
	 * @version 1.0
	 */
	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface Ipv6
	{

	}

	/**
	 * 字符串必须是Ip
	 * 
	 * @author Elva
	 * @date 2011-2-7
	 * @version 1.0
	 */
	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface Ip
	{

	}

	/**
	 * 字符串必须是ISBN
	 * 
	 * @author Elva
	 * @date 2011-2-7
	 * @version 1.0
	 */
	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface ISBN
	{

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
	public @interface URL
	{

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
	public @interface Domain
	{
	}

}
