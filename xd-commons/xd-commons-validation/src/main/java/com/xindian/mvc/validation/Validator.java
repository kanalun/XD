package com.xindian.mvc.validation;

import java.lang.annotation.Annotation;

/**
 * 
 * 验证value值是否符合Annotation的描述
 * 
 * 相当于JSR330 ConstraintValidator
 * 
 * @author Elva
 * @date 2011-2-7
 * @version 1.0
 * @param <A>
 *            使用该验证器的注解
 * @param <T>
 *            被验证的类型
 */
public interface Validator<A extends Annotation, T>
{
	/**
	 * 验证value值是否符合Annotation的描述
	 * 
	 * @param value
	 *            需要被验证的值
	 * @param annotation
	 *            值上的注解
	 * @return 如果合法返回true,否则返回false
	 * @throws ValidatorException
	 *             验证过程中发生无法处理的例外
	 */
	public boolean validate(Object value, A annotation) throws ValidatorException;
}
