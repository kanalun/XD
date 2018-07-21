package com.xindian.commons.beanutils.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用特定的类型转换器将来自于那个
 * 
 * @author Elva
 * @date 2011-1-22
 * @version 1.0
 */
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface UseConverter
{
	/**
	 * 类型转换器类型,
	 * 
	 * @return
	 */
	Class<?>[] value();

	/**
	 * 是否还是用默认的类型转换器,
	 * 
	 * 如果是true的话在配置的类型转换器失败的时候尝试使用默认的类型转换器(如果有的话),
	 * 
	 * 否则,不予转换!
	 * 
	 * @return
	 */
	boolean useDefault() default false;
}
