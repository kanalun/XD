package com.xindian.mvc.i18n3;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 本地化资源
 * 
 * @author Elva
 * @date 2011-2-3
 * @version 1.0
 */
public interface LocalizableResource extends Localizable
{
	/**
	 * 标记资源
	 */
	@Target({ ElementType.TYPE })
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Resource
	{
		String namespace() default "";

		String name() default "";

		String encoding() default "ISO-8859-1";// 资源使用的编码

		boolean share() default false;// 资源是否与其他组件共享,比如通过:getText()方法得到资源==
	}

	/**
	 * Specifies the default locale for LocalizableResource in this file.
	 * 
	 * If not specified, the default is en_US.
	 * 
	 * 当LocalePriv没有提供本地信息的时候,使用这个作为默认的
	 */
	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface DefaultLocale
	{
		String value() default "en_US";
	}

	/**
	 * 标记资源是全局资源
	 */
	@Target({ ElementType.TYPE })
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Gloabal
	{
		// 全局资源可以没有自己的资源文件,查找方法会搜索全局,如果找不到返回默认的值
	}

	/**
	 * The key used for lookup of translated strings. If not present,
	 * 
	 * the key will be generated based on the annotation,
	 * 
	 * or the unqualified method name if it is not present.
	 */
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Key
	{
		String value();
	}

	/**
	 * Specifies the meaning of the translated string. For example,
	 * 
	 * to distinguish between multiple meanings of a word or phrase.
	 */
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface Meaning
	{
		String value();
	}

	/**
	 * Specifies a description of the string to be translated,
	 * 
	 * such as a note about the context.
	 */
	@Target({ ElementType.TYPE, ElementType.METHOD })
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Description
	{
		String value();
	}
}
