package com.xindian.mvc.conversion.converters;

import java.util.Date;
import java.util.Map;

import com.xindian.mvc.conversion.ConversionException;
import com.xindian.mvc.conversion.Converter;

@Deprecated
public class SQLDateConverter implements Converter
{
	@Override
	public Object convert(Map<String, Object> context, Class targetType, Object value) throws ConversionException
	{
		if (targetType.equals(Date.class))
		{

		}
		return null;
	}

}
