//package com.xindian.commons.beanutils;
//
//import java.lang.reflect.Array;
//import java.lang.reflect.Field;
//import java.lang.reflect.InvocationTargetException;
//import java.util.Map;
//
//import com.xindian.commons.beanutils.annotation.Type;
//import com.xindian.commons.conversion.ConversionUtils;
//
///**
// * @author Elva
// * @date 2011-1-20
// * @version 1.0
// */
//public class BeanUtilsTest
//{
//	static DefaultResolver resolver = new DefaultResolver();
//
//	/**
//	 * 将Map中的数据填充到BEAN中
//	 * 
//	 * @param bean
//	 * @param map
//	 * @throws SecurityException
//	 * @throws IllegalAccessException
//	 * @throws InstantiationException
//	 */
//	private static void populate(Object bean, Map<String, ?> map) throws SecurityException,
//			IllegalAccessException, InstantiationException
//	{
//		for (String name : map.keySet())
//		{
//			try
//			{
//				System.out.println("NAME:" + name);
//				setValue(bean, name, map.get(name));
//			} catch (NoSuchFieldException e)
//			{
//				e.printStackTrace();
//			}
//		}
//	}
//
//	/**
//	 * 
//	 * @param bean
//	 * @param name
//	 * @param value
//	 * @throws SecurityException
//	 * @throws IllegalAccessException
//	 * @throws NoSuchFieldException
//	 * @throws InstantiationException
//	 */
//	private static void setValue(Object bean, String name, Object value) throws SecurityException,
//			IllegalAccessException, NoSuchFieldException, InstantiationException
//	{
//		while (resolver.hasNested(name))
//		{
//			String next = resolver.next(name);
//			System.out.println("NEXT:" + next);
//			Object nestedBean = null;
//			if (bean instanceof Map)// bean 是否为map
//			{
//				// nestedBean = getPropertyOfMapBean((Map) bean, next);
//			} else if (resolver.isMapped(next))
//			{
//				System.out.println("getMappedProperty");
//				// nestedBean = getMappedProperty(bean, next);
//
//				// if(nestedBean == null)
//				{
//					// createNestBean
//				}
//			} else if (resolver.isIndexed(next))
//			{
//				System.out.println("getIndexedProperty");
//				// nestedBean = getIndexedProperty(bean, next);
//			} else
//			{
//				nestedBean = getOrCreate(bean, next);
//				// System.out.println("NAME:" + next + " 	TYPE:" +
//				// nestedBean.getClass() + " 	VALUE:" + nestedBean);
//			}
//			if (nestedBean == null)
//			{
//				throw new NestedNullException("Null property value for '" + name
//						+ "' on bean class '" + bean.getClass() + "'");
//			}
//			bean = nestedBean;
//			name = resolver.remove(name);
//		}
//		// 这是最后的值
//		// 将这个值用转换器写入
//		System.out.println("NAME:" + name);
//		Field field = getFiled(bean, name);
//		field.setAccessible(true);
//		// field.set(bean, ConversionUtils.convert(value, field.getType()));
//	}
//
//	static class User
//	{
//
//	}
//
//	static class Address
//	{
//		String city;
//
//		/**
//		 * @return the city
//		 */
//		public String getCity()
//		{
//			return city;
//		}
//
//		/**
//		 * @param city
//		 *            the city to set
//		 */
//		public void setCity(String city)
//		{
//			this.city = city;
//		}
//	}
//
//	// 将value注入到User中去,如果
//	public static void main(String args[]) throws IllegalAccessException,
//			InvocationTargetException, NoSuchMethodException, SecurityException,
//			NoSuchFieldException, InstantiationException
//	{
//		long start = System.currentTimeMillis();
//
//		// for (int i = 0; i < 100; i++)
//		{
//			User user = new User();
//
//			String name = "address.city";
//
//			String ss = "ss";
//
//			String ssValue = "s0";
//
//			String value = "HUZHOU";
//
//			Address address = new Address();
//
//			address.setCity("ZHEJIANG");
//
//			// setValue(user, name, value);
//
//			Object ss2 = getOrCreateArray(user, ss);
//
//			if (ss2 instanceof String[])
//			{
//				((String[]) ss2)[0] = "qccc";
//			}
//			// String s = ((String[][]) ss2)[0][0];
//			// System.out.println("s	 :	" + s);
//			System.out.println("Class:	" + ((String[]) ss2).getClass());
//
//			System.out.println("Length:	" + ((String[]) ss2).length);
//
//			System.out.println("Length:	" + ((String[]) ss2)[0]);
//
//		}
//		System.out.println("TIME:" + (System.currentTimeMillis() - start));
//	}
//
//	public static Object readField(Object bean, Field field) throws IllegalArgumentException,
//			IllegalAccessException
//	{
//		field.setAccessible(true);
//		return field.get(bean);
//	}
//
//	public static void writeField(Object bean, Object value, Field field)
//			throws IllegalArgumentException, IllegalAccessException
//	{
//		field.setAccessible(true);
//		field.set(bean, value);
//	}
//
//	// 1,检查bean的fieldName属性,查看这个属性是否存在,如果存在且为map.直接返回这个属性
//	// 如果存在但是不为map,返回null表示创建失败
//	// 2,检查
//	//
//
//	/**
//	 * 创建或者获得对象filed对象,该方法对于接口无法处理
//	 * 
//	 * 1,如果对象存在,返回该对象
//	 * 
//	 * 2,如果对象不存在,创建对象,返回该对象
//	 * 
//	 * @param bean
//	 * @param filedName
//	 * @return
//	 * @throws SecurityException
//	 * @throws IllegalAccessException
//	 * @throws NoSuchFieldException
//	 * @throws InstantiationException
//	 */
//	public static Object getOrCreate(Object bean, String filedName) throws SecurityException,
//			IllegalAccessException, NoSuchFieldException, InstantiationException
//	{
//		Object fieldValue = null;
//		Field field = getFiled(bean, filedName);
//
//		if (field != null)
//		{
//			field.setAccessible(true);
//			fieldValue = field.get(bean);// 1,尝试,获取这个引用的对象
//		}
//		if (fieldValue != null)// 1,if the filed has value just use it
//		{
//			System.out.println("1,Filed has a value,直接返回该对象[" + fieldValue.getClass() + "]");
//			return fieldValue;
//		} else
//		{
//			if (field.isAnnotationPresent(Type.class))// 2,如果声明了类型的
//			{
//				Class<?>[] types = field.getAnnotation(Type.class).value();
//				for (Class<?> type : types)
//				{
//					try
//					{
//						fieldValue = type.newInstance();
//						if (fieldValue != null)
//						{
//							System.out.println("2,根据类型注解创建对象[" + type + "]");
//
//							// field.set(bean, beappend);// set ths Value to
//							// Bean
//							writeField(bean, fieldValue, field);
//
//							return fieldValue;
//						}
//					} catch (Exception e)
//					{
//						e.printStackTrace();
//						continue;
//					}
//				}// ERROR,尝试了所有的类型注解
//				throw new IllegalArgumentException("3,杯具发生了,所有的注解的类型都无法创建对象!!");
//			} else
//			{
//				// 尝试以,判断数组,枚举类型,其他列表类型
//				fieldValue = field.getType().newInstance();
//				System.out.println("4,根据Filed类型创建对象[" + field.getType() + "]");
//				// field.set(bean, beappend);// set ths Value to Bean
//				writeField(bean, fieldValue, field);
//				return fieldValue;
//			}
//			// System.out.println("1NAME:" + name + " 	TYPE:" + f.getType() +
//			// " 	VALUE:" + nestedBean);
//			// nestedBean.getClass().get
//		}
//		// System.out.println("1");
//		// return nestedBean;
//	}
//
//	// Form item' user.address[1].city = "HUZHOU"
//
//	// 1,create/get user from Action
//	// 2,create/get address[2],
//	// 3,create city
//	// 4,put city to address[1]
//	// 5,pun put address[] to user
//
//	// 实现及数据结构:------------------------
//	// 1,检查表达式是否有嵌套,
//	// 2,如果存在嵌套,创建或者得到这个对象,然后压入栈底
//	// 3,循环检查2,知道没有嵌套位置
//	// 4,如果发现没有嵌套,创建这个对象,弹出栈顶,然后把它放入栈顶那个对象的name位置,
//	// 5,继续弹出 堆栈的元素是一个键值对(name,Object)
//	public static Object getOrCreateArray(Object bean, String filedName) throws SecurityException,
//			IllegalAccessException, NoSuchFieldException, InstantiationException
//	{
//		Object fieldValue = null;
//		Field field = getFiled(bean, filedName);
//
//		if (field != null)
//		{
//			field.setAccessible(true);
//			fieldValue = field.get(bean);// 1,尝试,获取这个引用的对象
//		}
//		if (fieldValue != null)// 1,if the filed has value just use it
//		{
//			System.out.println("1,Filed has a value,直接返回该对象[" + fieldValue.getClass() + "]");
//			return fieldValue;
//		} else
//		{
//			if (field.isAnnotationPresent(Type.class))// 2,如果声明了类型的
//			{
//				Class<?>[] types = field.getAnnotation(Type.class).value();
//				for (Class<?> type : types)
//				{
//					try
//					{
//						fieldValue = type.newInstance();
//						if (fieldValue != null)
//						{
//							System.out.println("2,根据类型注解创建对象[" + type + "]");
//
//							return fieldValue;
//						}
//					} catch (Exception e)
//					{
//						e.printStackTrace();
//						continue;
//					}
//				}// ERROR,尝试了所有的类型注解
//				throw new IllegalArgumentException("3,杯具发生了,所有的注解的类型都无法创建对象!!");
//			} else
//			{
//				// 尝试以,判断数组,枚举类型,其他列表类型
//				Class<?> type = field.getType();
//				if (type.isArray())
//				{
//					Class<?> elementType = type.getComponentType();// 数组元素类型
//					if (elementType.isArray())
//					{
//						throw new BeanUtilException("只支持一维数组的创建..");
//					} else
//					{
//						System.out.println("Array of: " + elementType);
//						return Array.newInstance(elementType, 1);
//					}
//					// System.out.println(" Length: " +
//					// Array.getLength(object));
//				}
//				System.out.println("4,根据Filed类型创建对象[" + field.getType() + "]");
//
//				return fieldValue;
//			}
//			// System.out.println("1NAME:" + name + " 	TYPE:" + f.getType() +
//			// " 	VALUE:" + nestedBean);
//			// nestedBean.getClass().get
//		}
//		// System.out.println("1");
//		// return nestedBean;
//	}
//}
