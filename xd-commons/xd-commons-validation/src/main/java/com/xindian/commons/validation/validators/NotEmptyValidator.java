package com.xindian.commons.validation.validators;

import java.lang.reflect.Array;
import java.util.Collection;

import com.xindian.commons.utils.AnnotationUtils;
import com.xindian.commons.validation.Validator;
import com.xindian.commons.validation.ValidatorException;
import com.xindian.commons.validation.Validation.NotEmpty;

/**
 * @author Elva
 * @date 2011-2-7
 * @version 1.0
 */
public class NotEmptyValidator implements Validator<NotEmpty, Object>
{
	@Override
	public boolean validate(Object value, NotEmpty annotation) throws ValidatorException
	{
		if (value == null)
		{
			return false;
		}
		if (value instanceof CharSequence)
		{
			if (AnnotationUtils.getValue("trim", Boolean.class, annotation))
			{
				return ((CharSequence) value).toString().trim().length() > 0;
			} else
			{
				return ((CharSequence) value).length() > 0;
			}
		}
		if (value instanceof Collection<?>)
		{
			return ((Collection<?>) value).size() > 0;
		}
		if (value.getClass().isArray())
		{
			return Array.getLength(value) > 0;
		}
		throw new ValidatorException("NotEmptyValidator support this type[" + value.getClass() + "]");
	}
}
