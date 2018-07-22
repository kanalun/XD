package com.xindian.commons.conversion.converters;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.commons.conversion.ConversionException;
import com.xindian.commons.utils.LocaleUtils;

/**
 * 将字符串转换为本地信息
 * 
 * @author Elva
 * @date 2011-2-11
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public class LocaleConverter extends AbstractConverter
{
	private static Logger logger = LoggerFactory.getLogger(LocaleConverter.class);

	@SuppressWarnings("unchecked")
	public Object convert(Map<String, Object> context, Class targetType, Object sourceValue) throws ConversionException
	{
		if (sourceValue == null)
		{
			return null;
		}
		if (targetType == Locale.class)
		{
			String locale = sourceValue.toString();
			return LocaleUtils.localeFromString(locale, null);
		}
		return super.convert(context, targetType, sourceValue);
	}

	public String convertToString(Map<String, Object> context, Object sourceValue) throws ConversionException
	{
		return sourceValue.toString();
	}

	public static void main(String args[])
	{
		LocaleConverter localeConverter = new LocaleConverter();
		Object r = localeConverter.convert(Collections.EMPTY_MAP, Locale.class, "zh");
		logger.debug(r + "");
		// assert r != null : "is null";
	}
}
