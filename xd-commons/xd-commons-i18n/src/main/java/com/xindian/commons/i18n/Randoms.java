package com.xindian.commons.i18n;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 随机
 * 
 * @author Elva
 * @date 2011-2-20
 * @version 1.0
 */
public interface Randoms
{
	/**
	 * 随机非固定长度字符串
	 * 
	 * @author Elva
	 * @date 2011-2-11
	 * @version 1.0
	 */
	@Target({ ElementType.METHOD, ElementType.FIELD })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface RandomString
	{
		/** 最小长度 */
		int minLenght() default 0;

		/** 最大长度 */
		int maxLength() default 256;

		char[] in();

		String[] ins();
	}

	/**
	 * 随机固定长度的字符串
	 * 
	 * @author Elva
	 * @date 2011-2-11
	 * @version 1.0
	 */
	@Target({ ElementType.METHOD, ElementType.FIELD })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface RandomFixedLengthString
	{
		int length() default -1;

		char[] in();

		String[] ins();
	}

	/**
	 * 随机Boolean
	 * 
	 * @author Elva
	 * @date 2011-2-11
	 * @version 1.0
	 */
	@Target({ ElementType.METHOD, ElementType.FIELD })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface RandomBoolean
	{
	}

	/**
	 * 随机Byte,这个整数会介于min和MAX之间(包含)
	 * 
	 * @author Elva
	 * @date 2011-2-11
	 * @version 1.0
	 */
	@Target({ ElementType.METHOD, ElementType.FIELD })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface RandomByte
	{
		byte min() default Byte.MIN_VALUE;

		byte max() default Byte.MAX_VALUE;
	}

	/**
	 * 随机整数,这个整数会介于MAX和MAX之间(包含)
	 * 
	 * @author Elva
	 * @date 2011-2-11
	 * @version 1.0
	 */
	@Target({ ElementType.METHOD, ElementType.FIELD })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface RandomInt
	{
		int min() default Integer.MIN_VALUE;

		int max() default Integer.MAX_VALUE;
	}

	/**
	 * 随机整数,这个整数会介于MAX和MAX之间(包含)
	 * 
	 * @author Elva
	 * @date 2011-2-11
	 * @version 1.0
	 */
	@Target({ ElementType.METHOD, ElementType.FIELD })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface RandomLong
	{
		long min() default Long.MIN_VALUE;

		long max() default Long.MAX_VALUE;
	}

}
