package com.xindian.beanutils.temp;

import java.beans.IndexedPropertyDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.MappedPropertyDescriptor;
import org.apache.commons.beanutils.expression.Resolver;

import com.xindian.beanutils.annotation.UseConverter;
import com.xindian.mvc.test.User;

public class BeanUtils
{
	public static void main(String[] args)
	{
		BeanUtils beanUtils = new BeanUtils();
		String name = "u.name";
		String value = "qcc";
	}

	// 自动类型转换
	// 数组转obj
	// 嵌套属性注入
	public static void populate(Object bean, Map<String, Object> properties)
	{
		// Do nothing unless both arguments have been specified
		if ((bean == null) || (properties == null))
		{
			return;
		}
		if (log.isDebugEnabled())
		{
			log.debug("BeanUtils.populate(" + bean + ", " + properties + ")");
		}

		// Loop through the property name/value pairs to be set
		Iterator entries = properties.entrySet().iterator();
		while (entries.hasNext())
		{
			// Identify the property name and value(s) to be assigned
			Map.Entry entry = (Map.Entry) entries.next();
			String name = (String) entry.getKey();
			if (name == null)
			{
				continue;
			}
			// Perform the assignment for this property
			setProperty(bean, name, entry.getValue());
		}
		// callSetter(bean,);
	}

	public static void setProperty(Object bean, String name, Object value) throws IllegalAccessException,
			InvocationTargetException
	{
		// Trace logging (if enabled)
		if (log.isTraceEnabled())
		{
			StringBuffer sb = new StringBuffer("  setProperty(");
			sb.append(bean);
			sb.append(", ");
			sb.append(name);
			sb.append(", ");
			if (value == null)
			{
				sb.append("<NULL>");
			} else if (value instanceof String)
			{
				sb.append((String) value);
			} else if (value instanceof String[])
			{
				String[] values = (String[]) value;
				sb.append('[');
				for (int i = 0; i < values.length; i++)
				{
					if (i > 0)
					{
						sb.append(',');
					}
					sb.append(values[i]);
				}
				sb.append(']');
			} else
			{
				sb.append(value.toString());
			}
			sb.append(')');
			log.trace(sb.toString());
		}

		// Resolve any nested expression to get the actual target bean
		Object target = bean;
		Resolver resolver = getPropertyUtils().getResolver();
		while (resolver.hasNested(name))
		{
			try
			{
				target = getPropertyUtils().getProperty(target, resolver.next(name));
				name = resolver.remove(name);
			} catch (NoSuchMethodException e)
			{
				return; // Skip this property setter
			}
		}
		if (log.isTraceEnabled())
		{
			log.trace("    Target bean = " + target);
			log.trace("    Target name = " + name);
		}

		// Declare local variables we will require
		String propName = resolver.getProperty(name); // Simple name of target
														// property
		Class type = null; // Java type of target property
		int index = resolver.getIndex(name); // Indexed subscript value (if any)
		String key = resolver.getKey(name); // Mapped key value (if any)

		// Calculate the property type
		if (target instanceof DynaBean)
		{
			DynaClass dynaClass = ((DynaBean) target).getDynaClass();
			DynaProperty dynaProperty = dynaClass.getDynaProperty(propName);
			if (dynaProperty == null)
			{
				return; // Skip this property setter
			}
			type = dynaProperty.getType();
		} else if (target instanceof Map)
		{
			type = Object.class;
		} else
		{
			PropertyDescriptor descriptor = null;
			try
			{
				descriptor = getPropertyUtils().getPropertyDescriptor(target, name);
				if (descriptor == null)
				{
					return; // Skip this property setter
				}
			} catch (NoSuchMethodException e)
			{
				return; // Skip this property setter
			}
			if (descriptor instanceof MappedPropertyDescriptor)
			{
				if (((MappedPropertyDescriptor) descriptor).getMappedWriteMethod() == null)
				{
					if (log.isDebugEnabled())
					{
						log.debug("Skipping read-only property");
					}
					return; // Read-only, skip this property setter
				}
				type = ((MappedPropertyDescriptor) descriptor).getMappedPropertyType();
			} else if (index >= 0 && descriptor instanceof IndexedPropertyDescriptor)
			{
				if (((IndexedPropertyDescriptor) descriptor).getIndexedWriteMethod() == null)
				{
					if (log.isDebugEnabled())
					{
						log.debug("Skipping read-only property");
					}
					return; // Read-only, skip this property setter
				}
				type = ((IndexedPropertyDescriptor) descriptor).getIndexedPropertyType();
			} else if (key != null)
			{
				if (descriptor.getReadMethod() == null)
				{
					if (log.isDebugEnabled())
					{
						log.debug("Skipping read-only property");
					}
					return; // Read-only, skip this property setter
				}
				type = (value == null) ? Object.class : value.getClass();
			} else
			{
				if (descriptor.getWriteMethod() == null)
				{
					if (log.isDebugEnabled())
					{
						log.debug("Skipping read-only property");
					}
					return; // Read-only, skip this property setter
				}
				type = descriptor.getPropertyType();
			}
		}

		// Convert the specified value to the required type
		Object newValue = null;
		if (type.isArray() && (index < 0))
		{ // Scalar value into array
			if (value == null)
			{
				String[] values = new String[1];
				values[0] = (String) value;
				newValue = getConvertUtils().convert((String[]) values, type);
			} else if (value instanceof String)
			{
				newValue = getConvertUtils().convert(value, type);
			} else if (value instanceof String[])
			{
				newValue = getConvertUtils().convert((String[]) value, type);
			} else
			{
				newValue = convert(value, type);
			}
		} else if (type.isArray())
		{ // Indexed value into array
			if (value instanceof String || value == null)
			{
				newValue = getConvertUtils().convert((String) value, type.getComponentType());
			} else if (value instanceof String[])
			{
				newValue = getConvertUtils().convert(((String[]) value)[0], type.getComponentType());
			} else
			{
				newValue = convert(value, type.getComponentType());
			}
		} else
		{ // Value into scalar
			if ((value instanceof String) || (value == null))
			{
				newValue = getConvertUtils().convert((String) value, type);
			} else if (value instanceof String[])
			{
				newValue = getConvertUtils().convert(((String[]) value)[0], type);
			} else
			{
				newValue = convert(value, type);
			}
		}

		// Invoke the setter method
		try
		{
			getPropertyUtils().setProperty(target, name, newValue);
		} catch (NoSuchMethodException e)
		{
			throw new InvocationTargetException(e, "Cannot set " + propName);
		}

	}

	public static void callSetter(Object target, PropertyDescriptor prop, Object value) throws BeanException
	{
		Method setter = prop.getWriteMethod();

		if (setter == null)
		{
			return;
		}

		Class<?>[] paramTypes = setter.getParameterTypes();
		try
		{
			// convert types for some popular ones
			if (value != null)
			{
				if (value instanceof java.util.Date)
				{
					if (paramTypes[0].getName().equals("java.sql.Date"))
					{
						value = new java.sql.Date(((java.util.Date) value).getTime());
					} else if (paramTypes[0].getName().equals("java.sql.Time"))
					{
						value = new java.sql.Time(((java.util.Date) value).getTime());
					} else if (paramTypes[0].getName().equals("java.sql.Timestamp"))
					{
						value = new java.sql.Timestamp(((java.util.Date) value).getTime());
					}
				}
			}

			// Don't call setter if the value object isn't the right type
			if (isCompatibleType(value, paramTypes[0]))
			{
				setter.invoke(target, new Object[] { value });
			} else
			{
				UseConverter converter = ConverterFactory.getConverter(value.getClass(), paramTypes[0]);
				if (converter != null)
				{
					setter.invoke(target, converter.convert(null, value));
				} else
				{
					throw new BeanException("Cannot set " + prop.getName() + ": incompatible types. Type:" + value.getClass()
							+ "  != " + paramTypes[0]);
				}
			}

		} catch (IllegalArgumentException e)
		{
			throw new BeanException("Cannot set " + prop.getName() + ": " + e.getMessage());

		} catch (IllegalAccessException e)
		{
			throw new BeanException("Cannot set " + prop.getName() + ": " + e.getMessage());

		} catch (InvocationTargetException e)
		{
			throw new BeanException("Cannot set " + prop.getName() + ": " + e.getMessage());
		}
	}

	/**
	 * 判断是否为兼容类型
	 * 
	 * @param value
	 * @param type
	 * @return
	 */
	private static boolean isCompatibleType(Object value, Class<?> type)
	{
		// Do object check first, then primitives
		if (value == null || type.isInstance(value))
		{
			return true;

		} else if (type.equals(Integer.TYPE) && Integer.class.isInstance(value))
		{
			return true;

		} else if (type.equals(Long.TYPE) && Long.class.isInstance(value))
		{
			return true;

		} else if (type.equals(Double.TYPE) && Double.class.isInstance(value))
		{
			return true;

		} else if (type.equals(Float.TYPE) && Float.class.isInstance(value))
		{
			return true;

		} else if (type.equals(Short.TYPE) && Short.class.isInstance(value))
		{
			return true;

		} else if (type.equals(Byte.TYPE) && Byte.class.isInstance(value))
		{
			return true;

		} else if (type.equals(Character.TYPE) && Character.class.isInstance(value))
		{
			return true;

		} else if (type.equals(Boolean.TYPE) && Boolean.class.isInstance(value))
		{
			return true;

		} else
		{
			return false;
		}

	}

}
