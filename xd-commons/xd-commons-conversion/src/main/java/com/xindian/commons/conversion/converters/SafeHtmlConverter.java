package com.xindian.commons.conversion.converters;

import java.util.Map;

import com.xindian.commons.conversion.ConversionException;
import com.xindian.commons.utils.SafeHtml;

/**
 * 将类型转换为SafeHtml
 * 
 * @author Elva
 * @date 2011-2-20
 * @version 1.0
 */
public class SafeHtmlConverter extends AbstractConverter
{
	@SuppressWarnings("rawtypes")
	@Override
	public Object convert(Map<String, Object> context, Class targetType, Object sourceValue) throws ConversionException
	{
		if (targetType.equals(SafeHtml.class))
		{
			return new SafeHtml(sourceValue.toString());
		}
		return super.convert(context, targetType, sourceValue);
	}
}
