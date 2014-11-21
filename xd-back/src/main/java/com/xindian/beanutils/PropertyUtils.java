package com.xindian.beanutils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.mvc.test.User;

public class PropertyUtils
{
	private static Logger logger = LoggerFactory.getLogger(PropertyUtils.class);

	public static Property[] getProperties(Class<?> beanClass, Class<?> stopClass)
	{
		try
		{
			BeanInfo beanInfo = null;

			if (stopClass == null)
			{
				beanInfo = Introspector.getBeanInfo(beanClass);
			} else
			{
				beanInfo = Introspector.getBeanInfo(beanClass, stopClass);
			}
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			if (propertyDescriptors == null)
			{
				return new Property[0];
			}
			Property[] properties = new DefaultProperty[propertyDescriptors.length];
			for (int i = 0; i < properties.length; i++)
			{
				properties[i] = new DefaultProperty(propertyDescriptors[i], beanClass, stopClass);
			}
			return properties;
		} catch (IntrospectionException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public static Property getProperty(Class<?> type, Class<?> stopClass, String name)
	{
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Property[] properties = getProperties(User.class, null);
		for (Property property : properties)
		{
			logger.debug(property.getName());
		}
	}
}
