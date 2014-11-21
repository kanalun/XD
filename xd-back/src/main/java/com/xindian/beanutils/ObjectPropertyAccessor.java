package com.xindian.beanutils;

import java.beans.BeanInfo;
import java.beans.IndexedPropertyDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * setter/getter
 * 
 * @author Elva
 * @date 2011-3-7
 * @version 1.0
 */
public class ObjectPropertyAccessor implements PropertyAccessor
{
	private static Logger logger = LoggerFactory.getLogger(ObjectPropertyAccessor.class);

	@Override
	public Object getProperty(Map context, Object target, Object name)
	{
		Class type = target.getClass();

		try
		{
			BeanInfo beanInfo = Introspector.getBeanInfo(type);
		} catch (IntrospectionException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public PropertyDescriptor getPropertyDescriptor(Class beanClass, String name)
	{
		PropertyDescriptor[] descriptors = getPropertyDescriptors(beanClass);
		if (descriptors != null)
		{
			for (int i = 0; i < descriptors.length; i++)
			{
				if (name.equals(descriptors[i].getName()))
				{
					return (descriptors[i]);
				}
			}
		}
		return null;
	}

	private WeakFastHashMap/* <Class,PropertyDescriptor[]> */descriptorsCache = null;

	public PropertyDescriptor[] getPropertyDescriptors(Class beanClass)
	{
		if (beanClass == null)
		{
			throw new IllegalArgumentException("No bean class specified");
		}

		// Look up any cached descriptors for this bean class
		PropertyDescriptor[] descriptors = null;
		descriptors = (PropertyDescriptor[]) descriptorsCache.get(beanClass);
		if (descriptors != null)
		{
			return descriptors;
		}

		// Introspect the bean and cache the generated descriptors
		BeanInfo beanInfo = null;
		try
		{
			beanInfo = Introspector.getBeanInfo(beanClass);
		} catch (IntrospectionException e)
		{
			return (new PropertyDescriptor[0]);
		}
		descriptors = beanInfo.getPropertyDescriptors();
		if (descriptors == null)
		{
			descriptors = new PropertyDescriptor[0];
		}
		// ----------------- Workaround for Bug 28358 --------- START
		// ------------------
		//
		// The following code fixes an issue where IndexedPropertyDescriptor
		// behaves
		// Differently in different versions of the JDK for 'indexed' properties
		// which
		// use java.util.List (rather than an array).
		//
		// If you have a Bean with the following getters/setters for an indexed
		// property:
		//
		// public List getFoo()
		// public Object getFoo(int index)
		// public void setFoo(List foo)
		// public void setFoo(int index, Object foo)
		//
		// then the IndexedPropertyDescriptor's getReadMethod() and
		// getWriteMethod()
		// behave as follows:
		//
		// JDK 1.3.1_04: returns valid Method objects from these methods.
		// JDK 1.4.2_05: returns null from these methods.
		//
		for (int i = 0; i < descriptors.length; i++)
		{
			if (descriptors[i] instanceof IndexedPropertyDescriptor)
			{
				IndexedPropertyDescriptor descriptor = (IndexedPropertyDescriptor) descriptors[i];
				String propName = descriptor.getName().substring(0, 1).toUpperCase() + descriptor.getName().substring(1);

				if (descriptor.getReadMethod() == null)
				{
					String methodName = descriptor.getIndexedReadMethod() != null ? descriptor.getIndexedReadMethod().getName()
							: "get" + propName;
					Method readMethod = MethodUtils.getMatchingAccessibleMethod(beanClass, methodName, EMPTY_CLASS_PARAMETERS);
					if (readMethod != null)
					{
						try
						{
							descriptor.setReadMethod(readMethod);
						} catch (Exception e)
						{
							logger.error("Error setting indexed property read method", e);
						}
					}
				}
				if (descriptor.getWriteMethod() == null)
				{
					String methodName = descriptor.getIndexedWriteMethod() != null ? descriptor.getIndexedWriteMethod().getName()
							: "set" + propName;
					Method writeMethod = MethodUtils.getMatchingAccessibleMethod(beanClass, methodName, LIST_CLASS_PARAMETER);
					if (writeMethod == null)
					{
						Method[] methods = beanClass.getMethods();
						for (int j = 0; j < methods.length; j++)
						{
							if (methods[j].getName().equals(methodName))
							{
								Class[] parameterTypes = methods[j].getParameterTypes();
								if (parameterTypes.length == 1 && List.class.isAssignableFrom(parameterTypes[0]))
								{
									writeMethod = methods[j];
									break;
								}
							}
						}
					}
					if (writeMethod != null)
					{
						try
						{
							descriptor.setWriteMethod(writeMethod);
						} catch (Exception e)
						{
							logger.error("Error setting indexed property write method", e);
						}
					}
				}
			}
		}
		descriptorsCache.put(beanClass, descriptors);
		return descriptors;

	}

	@Override
	public void setProperty(Map context, Object target, Object name, Object value)
	{

	}
}
