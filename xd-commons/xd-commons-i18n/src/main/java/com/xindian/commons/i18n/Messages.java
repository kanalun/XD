package com.xindian.commons.i18n;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TODO 添加复数规则<br/>
 * 
 * 相比于Constants,Messages提供更多的方法:为接口方法添加参数,对文本进行格式化 <br/>
 * 
 * 事实上Constants接口中的方法仍然,但是为了区分语意,我们还是建议在不同的情况下
 * 
 * 区分使用它们:Constants为程序提供简单的常量,如果需要构造复杂的Message,请使用
 * 
 * Constants表示常量,换句话说他的值应该在任何情况下都不改变的(只读的) ,
 * 
 * 额外message应该只返回String类型
 * 
 * 只存在语意上的差别
 * 
 * @author Elva
 * @date 2011-2-4
 * @version 1.0
 */
public interface Messages extends Constants
{
	/**
	 * Default text to be used if no translation is found
	 * 
	 * (and also used as the source for translation).
	 * 
	 * Format should be that expected by {@link java.text.MessageFormat}.
	 * 
	 * <p>
	 * Example: <code><pre>
	 *   &#64;DefaultMessage("Don''t panic - you have {0} widgets left")
	 *   String example(int count)
	 * </pre></code>
	 * </p>
	 */
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface DefaultMessage
	{
		String value();
	}

	/**
	 * An example of the annotated parameter to assist translators.
	 * 
	 * <p>
	 * Example: <code><pre>
	 *   String example(&#64;Example("/etc/passwd") String fileName)
	 * </pre></code>
	 * </p>
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.PARAMETER)
	public @interface Example
	{
		String value();
	}

	/**
	 * 提供对参数格式化的支持,对于不同的
	 * 
	 * @author Elva
	 * @date 2011-2-6
	 * @version 1.0
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.METHOD, ElementType.PARAMETER })
	@Documented
	public @interface Fomart
	{
		String locale() default "";

		/**
		 * YYYY-MM-dd
		 * 
		 * @return
		 */
		String fomart() default "";

		/**
		 * 选择合适的格式化工具,对于Date/等常规按照类型进行格式化
		 * 
		 * @return
		 */
		Class<?> fomarter() default Object.class;
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.METHOD, ElementType.PARAMETER })
	@Documented
	public @interface Fomarts
	{
		Fomart[] value();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.METHOD, ElementType.PARAMETER })
	@Documented
	public @interface EscapeXml
	{

	}
}
