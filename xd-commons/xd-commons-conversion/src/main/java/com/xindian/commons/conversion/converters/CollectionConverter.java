package com.xindian.commons.conversion.converters;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.commons.conversion.ConversionException;
import com.xindian.commons.conversion.ConverterFactory;

/**
 * 将数组,字符串,集合,等转换成特定的集合对象
 * 
 * @author Elva
 * @date 2011-2-14
 * @version 1.0
 */

@SuppressWarnings("rawtypes")
public class CollectionConverter extends AbstractConverter
{
	private static Logger logger = LoggerFactory.getLogger(CollectionConverter.class);

	@Override
	public Object convert(Map<String, Object> context, Class targetType,
			Object sourceValue) throws ConversionException
	{
		Object co;
		try
		{
			co = targetType.newInstance();
			if (co instanceof Collection<?>)//
			{
				if (sourceValue instanceof String)// String -> collection
				{
					String[] stringArray = (String[]) string2StringArray(context,
							(String) sourceValue);// String
													// ->
													// String[]
					if (stringArray == null)// FIXME
					{
						throw new ConversionException("StringArray Null");
					} else
					{
						Class<?> targetElementType = (Class<?>) context
								.get(CONTEXT_ELEMENT_TYPE_KEY);
						if (targetElementType == null)
						{
							targetElementType = String.class;
						}
						Object arrayTypeArray = Array.newInstance(targetElementType, 0);
						Object array = array2OtherTypeArray(context,
								arrayTypeArray.getClass(), stringArray);// String[]->
																		// Type[]
						int l = Array.getLength(array);
						for (int i = 0; i < l; i++)
						{
							// TODO USE Collections.addAll();? put array to Collection
							((Collection) co).add(Array.get(array, i));
						}
						return co;
					}
				}
				// array -> collection
				else if (sourceValue.getClass().isArray())
				{
					Class<?> targetElementType = (Class<?>) context
							.get(CONTEXT_ELEMENT_TYPE_KEY);
					if (targetElementType == null)// 对元素不做类型转换,只改变容器
					{
						targetElementType = sourceValue.getClass().getComponentType();
					}
					// 这个数组并不做容器使用相当于new
					// T[0],只作为参数让程序知道数组元素的类型
					Object arrayTypeArray = Array.newInstance(targetElementType, 0);
					Object array = array2OtherTypeArray(context,
							arrayTypeArray.getClass(), sourceValue);
					int l = Array.getLength(array);
					for (int i = 0; i < l; i++)
					{
						((Collection) co).add(Array.get(array, i));
					}
					return co;
				} else if (sourceValue instanceof Collection<?>)
				{
					Class<?> elementType = (Class<?>) context
							.get(CONTEXT_ELEMENT_TYPE_KEY);
					if (elementType != null)
					{
						for (Iterator<?> sv = ((Collection<?>) sourceValue).iterator(); sv
								.hasNext();)
						{
							try
							{
								// convert element to targetElementType
								// TODO 优化一下:相同元素使用相同的类型转换器
								Object o = ConverterFactory.convert(context, elementType,
										sv.next());
								((Collection) co).add(o);
							} catch (ConversionException e)
							{
								throw new ConversionException("无法转换子元素....");
							}
						}
						return co;
					} else
					{
						if (sourceValue.getClass().equals(targetType))// 不对元素进行类型转换,同时也不对容器进行类型转换
						{
							return sourceValue;
						} else
						{
							((Collection) co).addAll((Collection<?>) sourceValue);// 直接转换容器,改变容器
							return co;
						}
					}
				} else
				{
					throw new ConversionException("无法支持");
				}
			} else
			{
				throw new ConversionException("");
			}
		} catch (InstantiationException e)
		{
			e.printStackTrace();
			throw new ConversionException();
		} catch (IllegalAccessException e)
		{
			e.printStackTrace();
			throw new ConversionException();
		}
	}

	public static void main(String args[])
	{
		CollectionConverter collectionConverter = new CollectionConverter();

		Map<String, Object> context = new HashMap<String, Object>();

		// context.put(CONTEXT_ELEMENT_TYPE_KEY, Integer.class);

		// context.put(CONTEXT_LOCALE_KEY, Locale.CHINA);
		logger.debug("=========="
				+ collectionConverter.convert(context, Vector.class,
						new int[] { 55, 2, 3 }));

		List arrayList = new ArrayList();
		for (int i = 0; i < 10; i++)
		{
			arrayList.add("" + i);
		}
		logger.debug("=========="
				+ collectionConverter.convert(context, Vector.class, arrayList));

		logger.debug("=========="
				+ ((Vector) collectionConverter.convert(context, Vector.class,
						new String[] { "a", "b", "c" })).get(0).getClass());
		logger.debug("=========="
				+ ((Vector) collectionConverter.convert(context, Vector.class,
						new BigDecimal[] { new BigDecimal("1") })).get(0).getClass());

		logger.debug("==========ccc"
				+ ((Vector) collectionConverter.convert(context, Vector.class, arrayList))
						.get(0).getClass());

		logger.debug("=========="
				+ ((Vector) collectionConverter.convert(context, Vector.class, "1,2,3"))
						.get(0));

	}
}
