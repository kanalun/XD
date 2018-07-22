package com.xindian.commons.conversion.converters;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.commons.conversion.ConversionException;
import com.xindian.commons.conversion.Converter;

/**
 * 将其他类型转换为LONG:已经废弃,使用NumberConverter
 * 
 * @author Elva
 * @date 2011-2-10
 * @version 1.0
 */
@Deprecated
@SuppressWarnings("rawtypes")
public class LongConverter implements Converter
{
	private static Logger logger = LoggerFactory.getLogger(LongConverter.class);

	@Override
	public Object convert(Map<String, Object> context, Class targetType,
			Object sourceValue) throws ConversionException
	{
		if (sourceValue instanceof Long)
		{
			logger.debug("SourceValue[" + sourceValue
					+ "] is Long/long type, no need to convert");
			return sourceValue;
		}
		if (sourceValue instanceof String)
		{
			try
			{
				long value = Long.parseLong((String) sourceValue);
				logger.debug("convert SourceValue[" + sourceValue + "] to Long/long type");
				return value;
			} catch (NumberFormatException e)
			{
				throw new ConversionException();
			}
		}
		if (sourceValue instanceof Integer)
		{

		}
		return null;
	}
}
