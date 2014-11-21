package com.xindian.mvc.validation.validators;

import java.lang.annotation.Annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.mvc.utils.AnnotationUtils;
import com.xindian.mvc.validation.Validator;
import com.xindian.mvc.validation.ValidatorException;

/**
 * 
 * @author Elva
 * @date 2011-2-7
 * @version 1.0
 */
public class MaxLengthValidator extends AbstractLengthValidator implements Validator
{
	private static Logger logger = LoggerFactory.getLogger(MaxLengthValidator.class);

	@Override
	public boolean validate(Object value, Annotation annotation) throws ValidatorException
	{
		Long maxLength = AnnotationUtils.getValue(ANNOTATION_VALUE, Long.class, annotation);

		return validate(value, maxLength, getClosedInterval(annotation));
	}

	protected boolean validate(Object value, Long maxLength, boolean losedInterval) throws ValidatorException
	{
		if (value == null)// FIXME
		{
			return true;
		}
		if (maxLength == null)
		{
			throw new ValidatorException("MinLengthValidator3 Context does not have key[" + ANNOTATION_VALUE + "]");
		}
		Long lt = getValueLength(value);
		if (losedInterval)
		{
			logger.debug("<=");
			return lt <= maxLength;
		} else
		{
			logger.debug("<");
			return lt < maxLength;
		}

	}
}
