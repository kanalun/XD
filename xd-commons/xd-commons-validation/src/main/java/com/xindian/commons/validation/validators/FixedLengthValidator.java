package com.xindian.commons.validation.validators;

import java.lang.annotation.Annotation;

import com.xindian.commons.validation.ValidatorException;

/**
 * 固定长度验证器
 * 
 * @author Elva
 * @date 2011-2-7
 * @version 1.0
 */
public class FixedLengthValidator extends AbstractLengthValidator
{
	@Override
	public boolean validate(Object value, Annotation annotation) throws ValidatorException
	{
		Long fixdLength = getLength(annotation);
		return validate(value, fixdLength);
	}

	protected boolean validate(Object value, Long fixdLength) throws ValidatorException
	{
		if (value == null)// FIXME
		{
			if (fixdLength == null)
			{
				return true;
			} else
			{
				return false;
			}
		}
		if (fixdLength == null)
		{
			throw new ValidatorException("MinLengthValidator3 Context does not have key[" + ANNOTATION_VALUE + "]");
		}
		Long lt = getValueLength(value);
		return lt == fixdLength;
	}
}
