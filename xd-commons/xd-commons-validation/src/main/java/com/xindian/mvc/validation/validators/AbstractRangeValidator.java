package com.xindian.mvc.validation.validators;

import java.lang.annotation.Annotation;

import com.xindian.mvc.validation.Validator;
import com.xindian.mvc.validation.ValidatorException;

/**
 * @author Elva
 * @date 2011-2-8
 * @version 1.0
 */
public abstract class AbstractRangeValidator implements Validator
{

	@Override
	public boolean validate(Object value, Annotation annotation) throws ValidatorException
	{
		return false;
	}

	public static void main(String args[])
	{
		Byte a = 0;
		Integer b = 1;
	}
}
