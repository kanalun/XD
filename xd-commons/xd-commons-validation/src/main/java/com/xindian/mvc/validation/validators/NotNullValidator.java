package com.xindian.mvc.validation.validators;

import java.lang.annotation.Annotation;

import com.xindian.mvc.validation.Validator;
import com.xindian.mvc.validation.ValidatorException;

/**
 * 
 * @author Elva
 * @date 2011-2-7
 * @version 1.0
 */
public class NotNullValidator implements Validator
{
	@Override
	public boolean validate(Object value, Annotation annotation) throws ValidatorException
	{
		return value != null;
	}
}
