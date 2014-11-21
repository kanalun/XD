package com.xindian.mvc.validation.validators;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;

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
public abstract class AbstractLengthValidator implements Validator
{
	private static Logger logger = LoggerFactory.getLogger(AbstractLengthValidator.class);

	public static String ANNOTATION_VALUE = "value";

	public static String ANNOTATION_CLOSED_INTERVAL = "closedInterval";

	protected Long getLength(Annotation annotation)
	{
		Long length = AnnotationUtils.getValue(ANNOTATION_VALUE, Long.class, annotation);
		return length;
	}

	// closedInterval
	protected Boolean getClosedInterval(Annotation annotation)
	{
		Boolean closedInterval = AnnotationUtils.getValue(ANNOTATION_CLOSED_INTERVAL, Boolean.class, annotation);
		return closedInterval;
	}

	protected Long getValueLength(Object value) throws ValidatorException
	{
		Long lt = null;
		if (value instanceof CharSequence)// 字符序列
		{
			logger.debug("Value:[" + value + "] Is a CharSequence[" + value.getClass() + "]");
			lt = new Long(((CharSequence) value).length());

		} else if (value instanceof File)// 文件
		{
			logger.debug("Value:[" + value + "] Is a File[" + value.getClass() + "]");
			lt = ((File) value).length();

		} else if (value.getClass().isArray())// 数组
		{
			logger.debug("Value:[" + value + "] Is a [" + value.getClass().getComponentType() + "]Array]");
			lt = new Long(Array.getLength(value));
		}
		if (lt == null)
		{
			throw new ValidatorException("MinLengthValidator3 does not support this type[" + value.getClass() + "]");
		}
		return lt;
	}
}
