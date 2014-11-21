package com.xindian.beanutils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.NestedNullException;

import com.xindian.mvc.test.Address;
import com.xindian.mvc.test.User;

public class BeanUtils2
{
	static DefaultResolver resolver = new DefaultResolver();

	public static void setValue(Object bean, String name, Object value) throws SecurityException, IllegalAccessException,
			NoSuchFieldException, InstantiationException, IllegalArgumentException, InvocationTargetException,
			IntrospectionException
	{
		while (resolver.hasNested(name))
		{
			String next = resolver.next(name);
			System.out.println("NEXT:" + next);
			Object nestedBean = null;
			if (bean instanceof Map)// bean 是否为map
			{
				// nestedBean = getPropertyOfMapBean((Map) bean, next);
			} else if (resolver.isMapped(next))
			{
				System.out.println("getMappedProperty");
				// nestedBean = getMappedProperty(bean, next);
			} else if (resolver.isIndexed(next))
			{
				System.out.println("getIndexedProperty");
				// nestedBean = getIndexedProperty(bean, next);
			} else
			{
				nestedBean = getOrCreate(bean, next);

				PropertyDescriptor pd = getPropertyDescriptor(bean, next);
				callSetter(bean, pd, nestedBean);
				//
				// bean.set(next,nextBean)
				// System.out.println("NAME:" + next + " 	TYPE:" +
				// nestedBean.getClass() + " 	VALUE:" + nestedBean);
			}
			if (nestedBean == null)
			{
				throw new NestedNullException("Null property value for '" + name + "' on bean class '" + bean.getClass() + "'");
			}

			bean = nestedBean;
			name = resolver.remove(name);
		}
		// 这是最后的值
		// 将这个值用转换器写入
		System.out.println("NAME:" + name);
		PropertyDescriptor pd = getPropertyDescriptor(bean, name);
		if (pd == null)
		{
			return;// 没有这个属性,返回失败
		} else
		{
			callSetter(bean, pd, value);
		}
	}

	/**
	 * 调用Setter方法
	 * 
	 * @param target
	 * @param prop
	 * @param value
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static void callSetter(Object target, PropertyDescriptor prop, Object value) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException
	{
		Method setter = prop.getWriteMethod();
		if (setter == null)
		{
			return;
		}
		Class<?>[] paramTypes = setter.getParameterTypes();

		// 类型转换
		setter.invoke(target, new Object[] { ConvertUtils.convert(value, paramTypes[0]) });
	}

	public static Object callGetter(Object target, PropertyDescriptor prop) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException
	{
		Method setter = prop.getReadMethod();
		if (setter == null)
		{
			return null;
		}
		return setter.invoke(target);
	}

	/**
	 * 获得某个属性的描述符
	 * 
	 * @param bean
	 * @param propertyName
	 * @return
	 * @throws IntrospectionException
	 */
	public static PropertyDescriptor getPropertyDescriptor(Object bean, String propertyName) throws IntrospectionException
	{
		BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass(), Object.class);
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor p : propertyDescriptors)
		{
			if (p.getName().equals(propertyName))
			{
				return p;
			}
		}
		return null;
	}

	/**
	 * 创建或者获得对象filed对象,该方法对于接口无法处理
	 * 
	 * @param bean
	 * @param filedName
	 * @return
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 * @throws InstantiationException
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 */
	public static Object getOrCreate(Object bean, String propertyName) throws SecurityException, IllegalAccessException,
			NoSuchFieldException, InstantiationException, IntrospectionException, IllegalArgumentException,
			InvocationTargetException
	{
		Object fieldValue = null;

		PropertyDescriptor pd = getPropertyDescriptor(bean, propertyName);
		if (pd == null)
		{
			return null;// 没有这个属性,返回失败
		} else
		{
			fieldValue = callGetter(bean, pd);// get
		}
		if (fieldValue != null)// 1,if the filed has value just use it
		{
			System.out.println("1,Filed has a value,直接返回该对象[" + fieldValue.getClass() + "]");
			return fieldValue;
		} else
		{
			/*
			 * if (field.isAnnotationPresent(Type.class))// 2,如果声明了类型的 {
			 * Class<?>[] types = field.getAnnotation(Type.class).value(); for
			 * (Class<?> type : types) { try { fieldValue = type.newInstance();
			 * if (fieldValue != null) { System.out.println("2,根据类型注解创建对象[" +
			 * type + "]");
			 * 
			 * // field.set(bean, beappend);// set ths Value to Bean
			 * writeField(bean, fieldValue, field);
			 * 
			 * return fieldValue; } } catch (Exception e) { e.printStackTrace();
			 * continue; } }// ERROR,尝试了所有的类型注解 throw new
			 * IllegalArgumentException("3,杯具发生了,所有的注解的类型都无法创建对象!!"); } else
			 */
			{
				Class<?> type = pd.getPropertyType();
				if (type.isArray())
				{

					// System.out.println(" Length: " +
					// Array.getLength(object));
				}
				// 尝试以
				fieldValue = pd.getPropertyType().newInstance();

				// pd.getPropertyType().
				System.out.println("4,根据Filed类型创建对象[" + fieldValue.getClass() + "]");
				// field.set(bean, beappend);// set ths Value to Bean
				// writeField(bean, fieldValue, field);
				return fieldValue;
			}
			// System.out.println("1NAME:" + name + " 	TYPE:" + f.getType() +
			// " 	VALUE:" + nestedBean);
			// nestedBean.getClass().get
		}
		// System.out.println("1");
		// return nestedBean;
	}

	public static void main(String args[]) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException,
			SecurityException, NoSuchFieldException, InstantiationException, IllegalArgumentException, IntrospectionException
	{
		long start = System.currentTimeMillis();
		for (int i = 0; i < 1; i++)
		{
			User user = new User();

			String name = "address.city";

			String value = "HUZHOU";

			Address address = new Address();

			address.setCity("ZHEJIANG");

			setValue(user, name, value);

			System.out.println("USER'S ADDRESS.CITY IS[" + user.getAddress().getCity() + "]"
					+ (System.currentTimeMillis() - start));
		}
		System.out.println("TIME:" + (System.currentTimeMillis() - start));
	}
}
