package com.xindian.commons.conversion.converters;

import java.util.Map;

import com.xindian.commons.conversion.ConversionException;

public final class ClassConverter extends AbstractConverter
{

	protected Class getDefaultType()
	{
		return Class.class;
	}

	protected String convertToString(Object value)
	{
		return (value instanceof Class) ? ((Class) value).getName() : value.toString();
	}

	@Override
	public Object convert(Map<String, Object> context, Class targetType, Object sourceValue) throws ConversionException
	{
		try
		{
			return convertToType(targetType, sourceValue);
		} catch (Throwable e)
		{
			e.printStackTrace();
			throw new ConversionException();
		}
	}

	protected Object convertToType(Class type, Object value) throws Throwable
	{
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader != null)
		{
			try
			{
				return (classLoader.loadClass(value.toString()));
			} catch (ClassNotFoundException ex)
			{
				// Don't fail, carry on and try this class's class loader
				// (see issue# BEANUTILS-263)
			}
		}
		// Try this class's class loader
		classLoader = ClassConverter.class.getClassLoader();
		return (classLoader.loadClass(value.toString()));
	}

}
