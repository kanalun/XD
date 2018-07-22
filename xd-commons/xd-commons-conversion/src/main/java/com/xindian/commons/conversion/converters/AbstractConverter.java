package com.xindian.commons.conversion.converters;

import java.lang.reflect.Array;
import java.util.Map;
import java.util.regex.PatternSyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.commons.conversion.ConversionException;
import com.xindian.commons.conversion.Converter;
import com.xindian.commons.conversion.ConverterFactory;

/**
 * 1,是否使用默认值代替抛出异常
 * 
 * 2,是否使用默认类型代替异常
 * 
 * @author Elva
 * @date 2011-2-10
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractConverter implements Converter
{
	private static Logger logger = LoggerFactory.getLogger(AbstractConverter.class);

	public static final String DEFAULT_DELIMITER = ",";

	/**
	 * 从上下文中获得默认值
	 * 
	 * @param context
	 * @param targetType
	 * @return
	 */
	public Object getDefaultValueFromContext(Map<String, Object> context)
	{
		Object defaultValue = context.get(CONTEXT_DEFAULT_VALUE_KEY);
		return defaultValue;
	}

	@Override
	public Object convert(Map<String, Object> context, Class targetType,
			Object sourceValue) throws ConversionException
	{
		throw new ConversionException("unsupport to convert type["
				+ sourceValue.getClass() + "]to type[" + targetType + "]");
	}

	protected Object handleError(Map<String, Object> context, Class targetType,
			Throwable throwable) throws Throwable
	{
		Boolean useDefault = (Boolean) context.get(CONTEXT_OPTION_USE_DEFAULT_VALUE_KEY);
		if (useDefault != null && useDefault)
		{
			return getDefaultValueFromContext(context);
		} else
		{
			throw throwable;
		}
	}

	protected Object string2StringArray(Map<String, Object> context, String value)
	{
		String delimiter = (String) context.get(CONTEXT_DELIMITER_KEY);
		if (delimiter == null)
		{
			delimiter = DEFAULT_DELIMITER;
		}
		try
		{
			String[] strArray = value.split(delimiter);
			Boolean trim = (Boolean) context.get(CONTEXT_STRING_TRIM_KEY);
			if (trim != null && trim)
			{
				for (int i = 0; i < strArray.length; i++)
				{
					strArray[i] = strArray[i].trim();
				}
			}
			return strArray;
		} catch (PatternSyntaxException e)
		{
			throw new ConversionException("分隔符错误....,请使用正确的分隔符");
		}
	}

	/**
	 * 数组到数组(元素可能不同:需要对数组元素进行类型转换)
	 * 
	 * @param context
	 * @param targetType
	 * @param array
	 * @return
	 */
	public Object array2OtherTypeArray(Map<String, Object> context, Class targetType,
			Object array)
	{
		Class sourceType = array.getClass();
		if (sourceType.isArray())
		{
			Class targetElementType = targetType.getComponentType();
			Class sourceElementType = sourceType.getComponentType();
			if (targetElementType.equals(sourceElementType))// 元素类型 相同
			{
				logger.debug("no need to convert element");
				return array;// no need to convert element
			}
			final int length = Array.getLength(array);
			Object returnArray = Array.newInstance(targetElementType, length);
			for (int i = 0; i < length; i++)
			{
				try
				{
					// convert element to targetElementType
					// TODO 优化一下:相同元素使用相同的类型转换器
					Object o = ConverterFactory.convert(context, targetElementType,
							Array.get(array, i));
					Array.set(returnArray, i, o);// put to array
				} catch (ConversionException e)
				{
					throw new ConversionException("无法转换子元素....");
				}
			}
			return returnArray;
		} else
		{
			throw new ConversionException();
		}
	}

	private static final String PACKAGE = "com.xindian.mvc.conversion.converters.";

	String toString(Class type)
	{
		String typeName = null;
		if (type == null)
		{
			typeName = "null";
		} else if (type.isArray())
		{
			Class elementType = type.getComponentType();
			int count = 1;
			while (elementType.isArray())
			{
				elementType = elementType.getComponentType();
				count++;
			}
			typeName = elementType.getName();
			for (int i = 0; i < count; i++)
			{
				typeName += "[]";
			}
		} else
		{
			typeName = type.getName();
		}
		if (typeName.startsWith("java.lang.") || typeName.startsWith("java.util.")
				|| typeName.startsWith("java.math."))
		{
			typeName = typeName.substring("java.lang.".length());
		} else if (typeName.startsWith(PACKAGE))
		{
			typeName = typeName.substring(PACKAGE.length());
		}
		return typeName;
	}
}
