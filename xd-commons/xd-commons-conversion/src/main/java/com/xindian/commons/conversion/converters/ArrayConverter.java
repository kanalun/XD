package com.xindian.commons.conversion.converters;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.regex.PatternSyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.commons.conversion.ConversionException;
import com.xindian.commons.conversion.ConverterFactory;

/**
 * 1,不同元素的数组之间的转换(需要不同元素转换器的支持)
 * 
 * 2,字符串到特定数组之间的转换:使用特定分隔符,默认为","
 * 
 * 3,集合到数组的转换
 * 
 * 4,普通类型到特定数组
 * 
 * 5,特定数组->普通类型(是否只取第一个值?)
 * 
 * @author Elva
 * @date 2011-2-11
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public class ArrayConverter extends AbstractConverter
{
	private static Logger logger = LoggerFactory.getLogger(ArrayConverter.class);

	@Override
	public Object convert(Map<String, Object> context, Class targetType,
			Object sourceValue) throws ConversionException
	{
		// target type is a Array type
		if (targetType.isArray())
		{
			// 字符串->目标类型数组
			if (sourceValue instanceof String)
			{
				logger.debug("字符串->目标类型数组");
				// 字符串->字符串数组
				Object strArray = string2StringArray(context, (String) sourceValue);
				// 字符串数组->目标类型
				return array2OtherTypeArray(context, targetType, strArray);
			}
			// 集合->目标类型的数组
			if (sourceValue instanceof Collection<?>)
			{
				logger.debug("集合->目标类型的数组");
				// 集合->数组
				Object strArray = collection2Array(context, targetType,
						(Collection) sourceValue);
				// 数组->数组
				return array2OtherTypeArray(context, targetType, strArray);
			}
			// 数组->目标类型的数组(元素类型可能不同)
			if (sourceValue.getClass().isArray())
			{
				logger.debug("数组->数组(元素类型不同)");
				return array2OtherTypeArray(context, targetType, sourceValue);
			} else
			// 类型->类型数组//FIXME
			{
				logger.debug("类型->类型数组");
				Object v = Array.newInstance(targetType.getComponentType(), 1);
				Object o = null;
				// 目标数组元素类型!=现在的类型,需要进行类型转换
				if (targetType.getComponentType() != sourceValue.getClass())
				{
					o = ConverterFactory.convert(context, targetType.getComponentType(),
							sourceValue);
				} else
				{
					o = sourceValue;
				}
				Array.set(v, 0, o);// put to array
				return v;
			}
		}
		// target type is a not Array type 数组->类型
		else if (sourceValue.getClass().isArray())
		{
			logger.debug("数组->类型");
			return array2Type(context, targetType, sourceValue);
		}
		return super.convert(context, targetType, sourceValue);
	}

	/**
	 * 字符串到字符串数组
	 * 
	 * @param context
	 * @param targetType
	 * @param value
	 * @return
	 */
	@Override
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
	 * 将集合转为数组
	 * 
	 * @param context
	 * @param targetType
	 * @param collection
	 * @return
	 */
	public Object collection2Array(Map<String, Object> context, Class targetType,
			Collection collection)
	{
		return collection.toArray();
	}

	/**
	 * 数组->普通类型
	 * 
	 * @param context
	 * @param targetType
	 * @param array
	 * @return
	 */
	protected Object array2Type(Map<String, Object> context, Class targetType,
			Object array)
	{
		Class<?> sourceArrayType = array.getClass();
		if (!sourceArrayType.isArray())
		{
			throw new ConversionException("Source value type is not an array!");
		}
		Object o = null;
		try
		{
			o = Array.get(array, 0);
		} catch (ArrayIndexOutOfBoundsException e)// 如果数组第一个元素不存在(空的数组)
		{
			return null;// 直接返回null
		}
		logger.debug("ArrayType[" + sourceArrayType + "] to type [" + targetType + "]");
		if (o == null || sourceArrayType.getComponentType().equals(targetType))
		{
			return o;
		} else
		{
			return ConverterFactory.convert(context, targetType, o);
		}
	}

	/**
	 * 数组->数组(元素可能不同:需要对数组元素进行类型转换)
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
}