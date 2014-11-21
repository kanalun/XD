package com.xindian.mvc.validation.validators;

import java.lang.annotation.Annotation;

import com.xindian.mvc.utils.AnnotationUtils;
import com.xindian.mvc.validation.Validator;
import com.xindian.mvc.validation.ValidatorException;

/**
 * 让用户支持自定义的验证器,貌似这样不是很好,参数怎么办?
 * 
 * TODO 使用类似于JSR300的方式,让用户自定义注解
 * 
 * @author Elva
 * @date 2011-2-7
 * @version 1.0
 */
@Deprecated
public class CustomValidator implements Validator
{
	private static final String ANNOTATION_VALIDATOR = "validator";

	@Override
	@SuppressWarnings("unchecked")
	public boolean validate(Object value, Annotation annotation) throws ValidatorException
	{
		Class<Validator> customValidatorType = AnnotationUtils.getValue(ANNOTATION_VALIDATOR, Class.class, annotation);
		try
		{
			Validator customValidator = customValidatorType.newInstance();
			return customValidator.validate(value, annotation);

		} catch (InstantiationException e)
		{
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		return false;
	}
}
