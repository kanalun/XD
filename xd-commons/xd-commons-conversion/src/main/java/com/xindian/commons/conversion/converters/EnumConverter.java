package com.xindian.commons.conversion.converters;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.commons.conversion.ConversionException;

/**
 * 将字符串转换为枚举类型
 * 
 * @author Elva
 * @date 2011-2-10
 * @version 1.0
 */

@SuppressWarnings("rawtypes")
public class EnumConverter extends AbstractConverter
{
	private static Logger logger = LoggerFactory.getLogger(EnumConverter.class);

	public static enum Gender// for testing
	{
		ALL, MALE, FEMALE
	}

	private boolean support(Map<String, Object> context, Class targetType, Object value)
	{
		if (targetType.isEnum())// return targetType.isEnum()
		{
			return true;
		}
		return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Object convert(Map<String, Object> context, Class targetType,
			Object sourceValue) throws ConversionException
	{
		if (!targetType.isEnum())
		{
			throw new ConversionException("targetType[" + targetType
					+ "] is not a a Enum type");//
		}
		if (sourceValue instanceof String)// String->Enum
		{
			try
			{
				return Enum.valueOf(targetType, (String) sourceValue);
			} catch (IllegalArgumentException e)
			{
				logger.error("无法将字符串转化为枚举类型..");
				throw new ConversionException("无法将字符串转化为枚举类型.");// 无法将
			}
		}
		return super.convert(context, targetType, sourceValue);//
	}

	public static void main(String args[])
	{
		System.out.println((new EnumConverter().convert(null, Gender.class, "ALL"))
				.getClass());
	}
}
