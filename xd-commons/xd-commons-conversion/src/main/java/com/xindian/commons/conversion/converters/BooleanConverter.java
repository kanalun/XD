package com.xindian.commons.conversion.converters;

import java.util.Map;

import com.xindian.commons.conversion.ConversionException;

/**
 * 布尔类型转换器
 * 
 * @author Elva
 * @date 2011-2-11
 * @version 1.0
 */
public class BooleanConverter extends AbstractConverter
{
	/**
	 * The set of strings that are known to map to Boolean.TRUE.
	 */
	private static String[] trueStrings = { "true", "yes", "y", "on", "1" };

	/**
	 * The set of strings that are known to map to Boolean.FALSE.
	 */
	private static String[] falseStrings = { "false", "no", "n", "off", "0" };

	@Override
	public Object convert(Map<String, Object> context, Class targetType, Object sourceValue) throws ConversionException
	{
		String stringValue = sourceValue.toString().toLowerCase();
		for (int i = 0; i < trueStrings.length; ++i)
		{
			if (trueStrings[i].equals(stringValue))
			{
				return Boolean.TRUE;
			}
		}
		for (int i = 0; i < falseStrings.length; ++i)
		{
			if (falseStrings[i].equals(stringValue))
			{
				return Boolean.FALSE;
			}
		}
		throw new ConversionException("Cna't convert value[" + sourceValue + "]to a Boolean");
		// return super.convert(context, targetType, sourceValue);
	}
}
