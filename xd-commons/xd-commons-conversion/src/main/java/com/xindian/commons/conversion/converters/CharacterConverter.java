package com.xindian.commons.conversion.converters;

import java.util.Map;

import com.xindian.commons.conversion.ConversionException;

/**
 * 
 * @author Elva
 * @date 2011-2-11
 * @version 1.0
 */
public final class CharacterConverter extends AbstractConverter
{
	protected Class getDefaultType()
	{
		return Character.class;
	}

	protected String convertToString(Object value)
	{
		String strValue = value.toString();
		return strValue.length() == 0 ? "" : strValue.substring(0, 1);
	}

	protected Object convertToType(Class type, Object value) throws Exception
	{
		return new Character(value.toString().charAt(0));
	}

	@Override
	public Object convert(Map<String, Object> context, Class targetType, Object sourceValue) throws ConversionException
	{
		return new Character(sourceValue.toString().charAt(0));
	}

}
