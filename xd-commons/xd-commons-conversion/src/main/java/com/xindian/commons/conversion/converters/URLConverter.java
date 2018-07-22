package com.xindian.commons.conversion.converters;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import com.xindian.commons.conversion.ConversionException;

/**
 * 将字符串类型的"玩意儿"转化成URL类型
 * 
 * @author Elva
 * @date 2011-2-11
 * @version 1.0
 */

@SuppressWarnings("rawtypes")
public class URLConverter extends AbstractConverter
{
	@Override
	public Object convert(Map<String, Object> context, Class targetType,
			Object sourceValue) throws ConversionException
	{
		try
		{
			return new URL(sourceValue.toString());
		} catch (MalformedURLException e)
		{
			e.printStackTrace();
			throw new ConversionException();
		}
		// return super.convert(context, targetType, sourceValue);
	}
}
