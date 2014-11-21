package com.xindian.beanutils.temp;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.NestedNullException;

import com.xindian.mvc.test.Address;
import com.xindian.mvc.test.User;

public class BACK
{
	/**
	 * 获得对象所有某个名字的Filed,包括存在父类中的,以及非共有的
	 * 
	 * @param bean
	 * @param name
	 * @return
	 * @throws SecurityException
	 */
	public static Field getFiled(Object bean, String name) throws SecurityException
	{
		System.out.println("从对象:[" + bean + "] TYPE:[" + bean.getClass() + "]查找Filed:[" + name + "]...");
		Field f = null;
		try
		{
			f = bean.getClass().getDeclaredField(name);// 获得这个对象中申明的Fileds
			System.out.println("	从对象中:[" + bean + "] TYPE:[" + bean.getClass() + "]获得Filed:[" + f.getName() + "]...");
		} catch (NoSuchFieldException e)
		{
			Class<?> b = bean.getClass();
			while (b.getSuperclass() != null && b.getSuperclass() != Object.class)// 使用循环从父对象中获得该Filed
			{
				try
				{
					f = b.getSuperclass().getDeclaredField(name);// 获得这个对象中申明的Fileds
					if (f != null)
					{
						System.out.println("	从[" + bean.getClass() + "]的父类TYPE:[" + b.getSuperclass() + "]获得该域:[" + f.getName()
								+ "]");
						// return f;
						break;
					}
				} catch (NoSuchFieldException e2)//
				{
					//
				} finally
				{
					b = b.getSuperclass();
				}
			}
		}
		return f;
	}

	/**
	 * 从类型和它的超类中获得名称为name的Filed
	 * 
	 * @param type
	 * @param name
	 * @return
	 * @throws SecurityException
	 */
	public static Field getFiled(Class<?> type, String name) throws SecurityException
	{
		System.out.println("从 TYPE:[" + type + "]查找Filed:[" + name + "]...");
		Field field = null;
		try
		{
			field = type.getDeclaredField(name);// 获得这个对象中申明的Fields,不包含父类的

			System.out.println("	从TYPE:[" + type + "]获得Filed:[" + field.getName() + "]...");
		} catch (NoSuchFieldException e)// 类型中找不到该域,准备从父类型中查找该域
		{
			Class<?> sp = type;
			while (sp.getSuperclass() != null && sp.getSuperclass() != Object.class)// 使用循环从父对象中获得该Filed
			{
				try
				{
					field = sp.getSuperclass().getDeclaredField(name);// 获得这个对象中申明的Fileds
					if (field != null)
					{
						System.out.println("	从[" + type + "]的父类TYPE:[" + sp.getSuperclass() + "]获得该域:[" + field.getName() + "]");
						// return f;
						break;
					}
				} catch (NoSuchFieldException e2)//
				{
					//
				} finally
				{
					sp = sp.getSuperclass();
				}
			}
		}
		return field;
	}

	public static Object getSimpleProperty2(Object bean, String name) throws IllegalAccessException, SecurityException,
			NoSuchFieldException
	{
		// bean.getClass().getSuperclass();
		System.out.println("从对象:[" + bean + "] TYPE:[" + bean.getClass() + "]查找Filed:[" + name + "]...");
		Field f = null;
		try
		{
			f = bean.getClass().getDeclaredField(name);// 获得这个对象中申明的Fileds
			System.out.println("	从对象中:[" + bean + "] TYPE:[" + bean.getClass() + "]获得Filed:[" + f.getName() + "]...");
		} catch (NoSuchFieldException e)
		{
			Class<?> b = bean.getClass();
			// f = bean.getClass().getDeclaredField(name);
			while (b.getSuperclass() != null && b.getSuperclass() != Object.class)// 使用循环从父对象中获得该Filed
			{
				try
				{
					f = b.getSuperclass().getDeclaredField(name);// 获得这个对象中申明的Fileds
					if (f != null)
					{
						System.out.println("	从[" + bean.getClass() + "]的父类TYPE:[" + b.getSuperclass() + "]获得该域:[" + f.getName()
								+ "]");
						// return f;
						break;
					}
				} catch (NoSuchFieldException e2)//
				{
					//
				} finally
				{
					b = b.getSuperclass();
				}
			}
		}

		if (f == null)
		{
			return null;
		}
		f.setAccessible(true);
		return f.get(bean);
	}

	public static void main(String args[]) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException,
			SecurityException, NoSuchFieldException, InstantiationException
	{
		User user = new User();

		String name = "address.city";

		String value = "HUZHOU";

		Address address = new Address();
		address.setCity("ZHEJIANG");

		Object bean = user;

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
		Field field = getFiled(bean, name);
		field.setAccessible(true);
		field.set(bean, value);

		System.out.println("USER'S ADDRESS.CITY IS[" + user.getAddress().getCity() + "]");
	}
}
