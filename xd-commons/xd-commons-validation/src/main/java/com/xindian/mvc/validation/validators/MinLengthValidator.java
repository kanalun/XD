package com.xindian.mvc.validation.validators;

import java.lang.annotation.Annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.mvc.utils.AnnotationUtils;
import com.xindian.mvc.validation.Validator;
import com.xindian.mvc.validation.ValidatorException;

/**
 * @author Elva
 * @date 2011-2-7
 * @version 1.0
 */
public class MinLengthValidator extends AbstractLengthValidator implements Validator
{
	private static Logger logger = LoggerFactory.getLogger(MinLengthValidator.class);

	protected boolean validate(Object value, Long minLength, boolean closedInterval) throws ValidatorException
	{
		if (value == null)// FIXME
		{
			if (minLength > 0)// value == 0 && minLength > 0
			{
				return false;
			} else
			{
				return true;
			}
		}
		if (minLength == null)
		{
			throw new ValidatorException("minLength can't be null[" + ANNOTATION_VALUE + "]");
		}
		Long lt = getValueLength(value);
		if (closedInterval)
		{
			logger.debug(">=");
			return lt >= minLength;
		} else
		{
			logger.debug(">");
			return lt > minLength;
		}

	}

	@Override
	public boolean validate(Object value, Annotation annotation) throws ValidatorException
	{
		Long minLength = AnnotationUtils.getValue(ANNOTATION_VALUE, Long.class, annotation);
		return validate(value, minLength, getClosedInterval(annotation));
	}
}
