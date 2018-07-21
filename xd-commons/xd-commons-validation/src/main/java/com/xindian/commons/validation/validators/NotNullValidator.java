package com.xindian.commons.validation.validators;

import java.lang.annotation.Annotation;

import com.xindian.commons.validation.Validator;
import com.xindian.commons.validation.ValidatorException;

/**
 * @author Elva
 * @date 2011-2-7
 * @version 1.0
 */
public class NotNullValidator implements Validator<Annotation, Object>
{
	@Override
	public boolean validate(Object value, Annotation annotation) throws ValidatorException
	{
		return value != null;
	}
}
