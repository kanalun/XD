package com.xindian.commons.validation;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于管理所有的注解验证器
 * 
 * @author Elva
 * @date 2011-2-7
 * @version 1.0
 */
public class ValidatorFactory
{
	private static Map<Class<? extends Annotation>, Validator> vs = new HashMap<Class<? extends Annotation>, Validator>();

	/**
	 * 
	 * @param validatorAnnotationType
	 * @return
	 */
	public static Validator getValidator(Class<?> validatorAnnotationType)
	{
		return vs.get(validatorAnnotationType);
	}

	/**
	 * 添加验证器
	 * 
	 * @param annotationType
	 * @param validator
	 */
	public static void addValidator(Class<? extends Annotation> annotationType, Validator validator)
	{
		synchronized (vs)
		{
			vs.put(annotationType, validator);
		}

	}
}
